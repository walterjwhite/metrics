package com.walterjwhite.metrics.modules.prometheus;

import com.netflix.spectator.atlas.AtlasConfig;
import javax.inject.Provider;

public class AtlasConfigProvider implements Provider<AtlasConfig> {
  protected final AtlasConfig atlasConfig = new DefaultAtlasConfig();

  @Override
  public AtlasConfig get() {
    return atlasConfig;
  }
}
