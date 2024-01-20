package game2048logic;

import game2048rendering.Side;

import static com.google.common.truth.Truth.assertWithMessage;

public class TestUtils {

    /**
     * Checks that performing a tilt in the specified direction on the before
     * Model results in the after Model
     */
    public static void checkTilt(Model before, Model after, Side direction) {
        String prevBoard = before.toString();
        before.tiltWrapper(direction);
        String errMsg = String.format("Board incorrect. Before tilting towards"
                        + " %s, your board looked like:%s%nAfter the call to"
                        + " tilt, we expected:%s%nBut your board looks like:%s.",
                direction, prevBoard, after, before);
        assertWithMessage(errMsg).that(before).isEqualTo(after);
    }
}
