package ru.otus.adapter;

/**
 * <p>
 * RotaryHammer - Перфоратор.
 *</p>
 * <img src="https://avatars.mds.yandex.net/get-mpic/5194541/img_id175880196850303322.jpeg/600x600"/>
 *
 * <p>
 * SDS-бур:
 * </p>
 * <img src="https://avatars.mds.yandex.net/get-mpic/5159019/img_id8891047601201361971.jpeg/600x600"/>
 *
 * <p>
 * SDS-коронка:
 * </p>
 * <img src="https://avatars.mds.yandex.net/get-mpic/5332113/img_id6607837877369596143.png/600x600"/>
 *
 * <p>
 * SDS-зубило:
 * </p>
 * <img src="https://avatars.mds.yandex.net/get-mpic/4525599/img_id2943802202743480706.jpeg/600x600"/>
 *
 *  <p>
 *  Простое сверло.
 *  Без SDS-хвостовика.
 *  </p>
 *  <img src="https://avatars.mds.yandex.net/get-mpic/5297750/img_id7034120764485480591.jpeg/600x600"/>
 *
 * <p>
 *  SDS-адаптер.
 * </p>
 * <img src="https://torg123.ru/upload/iblock/72d/72d4708464550764716a52b173c24f57.jpg"/>
 */

public class Demo {
    public static void main(String[] args) {
        new Demo().usePattern();
        new Demo().alternative();
    }

    public void usePattern() {
        var rotaryHammer = new RotaryHammer();
        var drill = new Drill();
        // Используем сверло через адаптер
        rotaryHammer.drill(new SDSadapter(drill));
    }

    public void alternative() {
        var rotaryHammer = new RotaryHammer();
        var drill = new Drill();
        // В нашем простом примере можно использовать и просто лямбду
        rotaryHammer.drill(() -> System.out.println(drill));
    }
}
