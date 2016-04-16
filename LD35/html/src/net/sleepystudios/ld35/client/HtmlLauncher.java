package net.sleepystudios.ld35.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import net.sleepystudios.ld35.LD35;

public class HtmlLauncher extends GwtApplication {
    @Override
    public GwtApplicationConfiguration getConfig () {
            return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener getApplicationListener () {
            return new LD35();
    }

	@Override
	public ApplicationListener createApplicationListener() {
		return null;
	}
}