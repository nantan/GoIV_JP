package com.kamron.pogoiv.pokeflycomponents.ocrhelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScanFieldAutomaticLocatorTest {

    private Context mContext;
    private Context mTargetContext;

    @Before
    public void setUp() {
        mContext = InstrumentationRegistry.getContext();
        mTargetContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void scan_VDF500() throws IOException {
        checkDevice(Device.VODAFONE_VDF_500);
    }

    @Test
    public void scan_Nexus5() throws IOException {
        checkDevice(Device.GOOGLE_NEXUS_5);
    }

    private void checkDevice(Device device) throws IOException {
        String[] pokemonInfoScreenFileNames = mContext.getAssets().list(device.infoScreensDirPath);
        for (String assetFileName : pokemonInfoScreenFileNames) {
            Bitmap bmp = BitmapFactory.decodeStream(mContext.getAssets()
                    .open(device.infoScreensDirPath + "/" + assetFileName));
            ScanFieldAutomaticLocator autoLocator = new ScanFieldAutomaticLocator(bmp, device.screenDensity);
            ScanFieldResults results = autoLocator.scan(null, null);
            checkScanFieldResults(device, assetFileName, results);
        }
    }

    private void checkScanFieldResults(Device device, String testAssetName, ScanFieldResults results) {
        // Execute checks on 'mon name area
        assertNotNull("File " + testAssetName + " on " + device.toString()
                        + ": 'mon name area wasn't detected",
                results.pokemonNameArea);
        assertTrue("File " + testAssetName + " on " + device.toString()
                        + ": 'mon name area doesn't contain the entire name."
                        + " Expected " + device.expectedNameArea + " got " + results.pokemonNameArea.toRectString(),
                results.pokemonNameArea.contains(device.expectedNameArea));
        assertTrue("File " + testAssetName + " on " + device.toString()
                        + ": 'mon name area looks to big to me!"
                        + " Expected " + device.expectedNameArea + " got " + results.pokemonNameArea.toRectString(),
                results.pokemonNameArea.width * results.pokemonNameArea.height
                        < 3 * device.expectedNameArea.width() * device.expectedNameArea.height());

        // Execute checks on 'mon type area
        assertNotNull("File " + testAssetName + " on " + device.toString()
                        + ": 'mon type area wasn't detected",
                results.pokemonTypeArea);
        assertTrue("File " + testAssetName + " on " + device.toString()
                        + ": 'mon type area doesn't contain the entire type."
                        + " Expected " + device.expectedTypeArea + " got " + results.pokemonTypeArea.toRectString(),
                results.pokemonTypeArea.contains(device.expectedTypeArea));
        assertTrue("File " + testAssetName + " on " + device.toString()
                        + ": 'mon type area looks to big to me!"
                        + " Expected " + device.expectedTypeArea + " got " + results.pokemonTypeArea.toRectString(),
                results.pokemonTypeArea.width * results.pokemonTypeArea.height
                        < 3 * device.expectedTypeArea.width() * device.expectedTypeArea.height());

        // Execute checks on 'mon candy name area
        assertNotNull("File " + testAssetName + " on " + device.toString()
                        + ": 'mon type area wasn't detected",
                results.candyNameArea);
        assertTrue("File " + testAssetName + " on " + device.toString()
                        + ": 'mon type area doesn't contain the entire type."
                        + " Expected " + device.expectedCandyNameArea + " got " + results.candyNameArea.toRectString(),
                results.candyNameArea.contains(device.expectedCandyNameArea));
        assertTrue("File " + testAssetName + " on " + device.toString()
                        + ": 'mon type area looks to big to me!"
                        + " Expected " + device.expectedCandyNameArea + " got " + results.candyNameArea.toRectString(),
                results.candyNameArea.width * results.candyNameArea.height
                        < 3 * device.expectedCandyNameArea.width() * device.expectedCandyNameArea.height());


        // TODO check all the other fields of ScanFieldResults
    }

}
