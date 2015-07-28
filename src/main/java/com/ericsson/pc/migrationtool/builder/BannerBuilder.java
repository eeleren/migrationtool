package com.ericsson.pc.migrationtool.builder;

import java.util.List;

import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.builder.banner.BannerConstants;
import com.ericsson.pc.migrationtool.builder.manual.ManualConstants;

public class BannerBuilder extends Builder {
	
	protected final String extension = BannerConstants.FILE_EXTENSION;

	@Override
	public Builder getNewInstance() {
		return new BannerBuilder();
	}

	@Override
	public void createAssets(List<Model> models) {
		// TODO Auto-generated method stub
	}
}
