package com.example.a12524.httpapi.method;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class ImageCut {
    public static List<Bitmap> split(Bitmap bitmap, int x_len, int y_len, int x_num, int y_num) {
            List<Bitmap> pieces = new ArrayList<Bitmap>(x_num * y_num);
            if(bitmap != null){
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                for (int i = 0; i < y_num; i++) {
                    for (int j = 0; j < x_num; j++) {
                        Bitmap piece;
                        int xValue = j * x_len;
                        int yValue = i * y_len;
                        piece = Bitmap.createBitmap(bitmap, xValue, yValue, x_len, y_len);
                        pieces.add(piece);
                    }
                }
            }
            return pieces;

    }

}
