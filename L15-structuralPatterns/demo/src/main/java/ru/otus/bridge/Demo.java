package ru.otus.bridge;

/**
 *
 * <p>
 * Карты: дебетовая, кредитная.
 * </p>
 * <p>
 * Платежные системы: VISA, MasterCard, MIR.
 * </p>
 *
 * <p>
 * Можем сделать такую иерархию:
 * </p>
 * <img src="https://www.planttext.com/api/plantuml/png/TOux2e1034Jxd28Ny0OMWhQszjbim8LTYoHs7julAeBDaCpBX6dCINqk6SOK1JafPpdd8YM86b0mLs97pcI6BMa4s1NhfQfmI4QjIan-UFX1dOIBl4CzcOlojlz4duFx0K0ZKzoghW00"/>
 *
 * <p>Но это неудобно. Проблема в том, что расширяем классы сразу в двух независимых плоскостях. Лучше так:</p>
 * <p>Паттерн Мост предлагает заменить наследование делегированием.
 * Для этого нужно выделить одну из таких "плоскостей" в отдельную иерархию
 * и ссылаться на объект этой иерархии,
 * вместо хранения его состояния и поведения внутри одного класса.</p>
 * <img src="https://www.planttext.com/api/plantuml/png/ROz12W8n34NtEKKkq0k8oC2u5Ge5zvasOg4jaiJ5W8UtphYmuEx_zpw1J6eazgm5xZannYony9uhruL5WHOreMWO2wdYJ98WDsEvKMJqj5Pk5bOrhl2Hw4uZnGC-XjK-ExwSmrauPTiw_k0pBryACDxQz8LwbjYz3__yDIlvW31nI_stDm00"/>
 */
public class Demo {
    public static void main(String[] args) {
        var card1 = new CreditCard(new VisaPS());
        card1.info();

        var card2 = new DebitCard(new MastercardPS());
        card2.info();

        var card3 = new DebitCard(new MirPS());
        card3.info();
    }
}
