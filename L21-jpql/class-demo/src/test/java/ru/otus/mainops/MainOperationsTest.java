package ru.otus.mainops;

import org.hibernate.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.core.HibernateUtils;
import ru.otus.mainops.model.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.core.HibernateUtils.*;

class MainOperationsTest {

    private Avatar avatar;
    private OtusStudent student;

    private SessionFactory sf;

    @BeforeEach
    void setUp() {
        avatar = new Avatar(0, "http://any-addr.ru/");
        student = new OtusStudent(0, "AnyName", avatar);

        sf = buildSessionFactory(OtusStudent.class, OtusTeacher.class, Avatar.class);
        sf.getStatistics().setStatisticsEnabled(true);
    }

    @DisplayName("persist не вставляет сущность в БД без транзакции")
    @Test
    public void shouldNeverPersistEntityToDBWhenTransactionDoesNotExists() {
        doInSession(sf, session -> session.persist(student));

        assertThat(sf.getStatistics().getPrepareStatementCount()).isEqualTo(0);
    }

    @DisplayName("persist вставляет сущность и ее связь в БД при наличии транзакции")
    @Test
    public void shouldNeverEntityWithRelationToDBWhenTransactionExists() {
        doInSessionWithTransaction(sf, session -> session.persist(student));

        assertThat(sf.getStatistics().getPrepareStatementCount()).isEqualTo(2);
    }

    @DisplayName("выкидывает исключение если вставляемая сущность в состоянии detached")
    @Test
    public void shouldThrowExceptionWhenPersistDetachedEntity() {
        var avatar = new Avatar(1L, "http://any-addr.ru/");
        assertThatCode(() ->
                doInSessionWithTransaction(sf, session -> session.persist(avatar))
        ).hasCauseInstanceOf(PersistentObjectException.class);
    }


    @DisplayName("persist выкидывает исключение если вставляемая сущность " +
            "содержит дочернюю в состоянии transient при выключенной каскадной операции PERSIST")
    @Test
    public void shouldThrowExceptionWhenPersistEntityWithChildInTransientStateAndDisabledCascadeOperation() {
        var teacher = new OtusTeacher(0, "AnyName", avatar);
        assertThatCode(() ->
                doInSessionWithTransaction(sf, session -> session.persist(teacher))
        ).hasCauseInstanceOf(TransientObjectException.class);
    }


    @DisplayName("изменения в сущности под управлением контекста попадают в БД " +
            "при закрытии сессии")
    @Test
    public void shouldSaveEntityChangesToDBAfterSessionClosing() {
        var newName = "NameAny";

        doInSessionWithTransaction(sf, session -> {
            session.persist(student);

            // Отключаем dirty checking (одно из двух)
            // session.setHibernateFlushMode(FlushMode.MANUAL);
            // session.detach(student);

            student.setName(newName);
        });

        assertThat(sf.getStatistics().getEntityUpdateCount()).isEqualTo(1);

        doInSessionWithTransaction(sf, session -> {
            var actualStudent = session.find(OtusStudent.class, student.getId());
            assertThat(actualStudent.getName()).isEqualTo(newName);
        });
    }


    @DisplayName("merge при сохранении transient сущности работает как persist," +
            "а при сохранении detached делает дополнительный запрос в БД")
    @Test
    public void shouldWorkAsPersistWhenSaveTransientEntityAndDoAdditionalSelectWhenSaveDetachedEntity() {
        doInSessionWithTransaction(sf, session -> session.merge(avatar));

        assertThat(sf.getStatistics().getEntityInsertCount()).isEqualTo(1);
        assertThat(sf.getStatistics().getEntityLoadCount()).isEqualTo(0);
        assertThat(sf.getStatistics().getEntityUpdateCount()).isEqualTo(0);

        avatar.setId(1L);
        avatar.setPhotoUrl("http://any-addr2.ru/");
        sf.getStatistics().clear();

        doInSessionWithTransaction(sf, session -> session.merge(avatar));

        assertThat(sf.getStatistics().getEntityLoadCount()).isEqualTo(1);
        assertThat(sf.getStatistics().getEntityUpdateCount()).isEqualTo(1);
        assertThat(sf.getStatistics().getEntityInsertCount()).isEqualTo(0);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @DisplayName("при доступе к LAZY полю за пределами сессии выкидывается исключение")
    @Test
    public void shouldThrowExceptionWhenAccessingToLazyField() {
        doInSessionWithTransaction(sf, session -> session.persist(student));

        OtusStudent actualStudent;
        try (var session = sf.openSession()) {
            actualStudent = session.find(OtusStudent.class, 1L);
        }
        assertThatCode(() -> actualStudent.getAvatar().getPhotoUrl())
                .isInstanceOf(LazyInitializationException.class);
    }

    @DisplayName("find загружает сущность со связями")
    @Test
    public void shouldFindEntityWithChildField() {
        doInSessionWithTransaction(sf, session -> session.persist(student));

        try (var session = sf.openSession()) {
            var actualStudent = session.find(OtusStudent.class, 1L);
            assertThat(actualStudent.getAvatar()).isNotNull().isEqualTo(avatar);
        }
    }
}