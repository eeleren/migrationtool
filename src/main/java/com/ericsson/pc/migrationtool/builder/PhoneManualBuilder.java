package com.ericsson.pc.migrationtool.builder;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.pc.migrationtool.bean.Model;
import com.ericsson.pc.migrationtool.bean.Phone;
import com.ericsson.pc.migrationtool.bean.PhoneManual;
import com.ericsson.pc.migrationtool.bean.VariantPhone;
import com.ericsson.pc.migrationtool.bean.msdp.PhoneAssetStructure;

public class PhoneManualBuilder  extends Builder {

	@Override
	public Builder getNewInstance() {
		return new PhoneManualBuilder();
	}

	@Override
	public void createAssets(List<Model> models) {
		PhoneManualBuilder builder = new PhoneManualBuilder();	
		
		List<PhoneManual> manuals = new ArrayList<PhoneManual>();
		
		for (Model m: models) {
			if (m instanceof PhoneManual) 
				manuals.add((PhoneManual) m);
		} 	
		
		builder.cleanOutputDir();
		logger.info("BUILDING EXECUTION STARTED...");
		
		for (PhoneManual pm: manuals) {
			createPhoneManualAsset(pm);
		}
		
		logger.info("BUILDER EXECUTION COMPLETED!");
		
	}
	
	public void createPhoneManualAsset(PhoneManual phoneManual) {
		PhoneAssetStructure asset = buildAssetStructure(phone);
		createXml(asset);
	}

		
	}

}
