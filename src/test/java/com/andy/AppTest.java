package com.andy;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.andy.display.DisplayMaster;

/**
 * 单元测试
 */
public class AppTest {
    
    /**
     * DisplayMaster 单元测试。
     */
    // @Test
    // public void testDisplayMaster() {
    //     var displayMaster = new DisplayMaster(4, 4);

    //     displayMaster.setBackgroundDisplayer(() -> {
    //         var result = new ArrayList<ArrayList<String>>();
    //         result.add(new ArrayList<String>(List.of(".", " ", ".", " ")));
    //         result.add(new ArrayList<String>(List.of(" ", ".", " ", ".")));
    //         result.add(new ArrayList<String>(List.of(".", " ", ".", " ")));
    //         result.add(new ArrayList<String>(List.of(" ", ".", " ", ".")));
    //         return result;
    //     });

    //     displayMaster.addContentDisplayer(() -> {
    //         var result = new ArrayList<ArrayList<String>>();
    //         result.add(new ArrayList<String>(List.of(" ", " ", " ", " ")));
    //         result.add(new ArrayList<String>(List.of(" ", "X", "X", " ")));
    //         result.add(new ArrayList<String>(List.of(" ", "X", "X", " ")));
    //         result.add(new ArrayList<String>(List.of(" ", " ", " ", " ")));
    //         return result;
    //     });

    //     var expectedDisplay = "\n".repeat(100) + "..  ..  \n  XXXX..\n..XXXX  \n  ..  ..\n";
    //     displayMaster.apply();
    //     var gettedDisplay = displayMaster.toString();

    //     assertEquals(expectedDisplay, gettedDisplay);
    // }
}
