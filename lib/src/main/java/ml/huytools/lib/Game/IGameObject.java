package ml.huytools.lib.Game;

import android.graphics.Canvas;

public interface IGameObject {
    void draw(Canvas canvas);
    boolean update();
}
