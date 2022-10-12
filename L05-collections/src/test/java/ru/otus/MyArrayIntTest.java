package ru.otus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class MyArrayIntTest {

    @Test
    @DisplayName("value from array")
    void setGetValue() throws Exception {
        int val = 45;
        int size = 1;
        MyArrayInt myArr = new MyArrayInt(size);

        //given
        int idx = size - 1;

        //when
        myArr.setValue(idx, val);

        //then
        assertThat(myArr.getValue(idx)).isEqualTo(val);
        assertThat(myArr.getSize()).isEqualTo(size);

        //given
        idx = size + 1;

        //when
        myArr.setValue(idx, val);

        //then
        assertThat(myArr.getValue(idx)).isEqualTo(val);
        assertThat(myArr.getSize()).isEqualTo(idx + 1);
    }

    @Test
    @DisplayName("value from array for index")
    void setGetValueSeq() throws Exception {
        //given
        int size = 100;
        MyArrayInt myArr = new MyArrayInt(size);

        //when
        for (int idx = 0; idx < size; idx++) {
            myArr.setValue(idx, idx);
        }

        //then
        for (int idx = 0; idx < size; idx++) {
            assertThat(myArr.getValue(idx)).isEqualTo(idx);
        }
    }

    @Test
    @DisplayName("value from array for index with initial size")
    void incSize() throws Exception {
        //given
        int sizeInit = 1;
        int sizeMax = 100;
        MyArrayInt myArr = new MyArrayInt(sizeInit);

        //then
        for (int idx = 0; idx < sizeMax; idx++) {
            myArr.setValue(idx, idx);
        }

        //when
        for (int idx = 0; idx < sizeMax; idx++) {
            assertThat(myArr.getValue(idx)).isEqualTo(idx);
        }
    }
}
