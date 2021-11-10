package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {

    @Override
    public char convert(int color) {
        if (color >= 224) {
            return '●';
        }
        if (color >= 192) {
            return '◉';
        }
        if (color >= 160) {
            return '◍';
        }
        if (color >= 128) {
            return '○';
        }
        if (color >= 96) {
            return '◌';
        }
        if (color >= 64) {
            return '◌';
        }
        if (color >= 32) {
            return '-';
        }
        if (color >= 0) {
            return ' ';
        }
        return 0;
    }
}
