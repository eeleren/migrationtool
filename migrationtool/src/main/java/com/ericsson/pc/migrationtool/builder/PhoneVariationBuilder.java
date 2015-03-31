package com.ericsson.pc.migrationtool.builder;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import com.ericsson.pc.migrationtool.bean.PhoneAsset;
import com.ericsson.pc.migrationtool.bean.Variation;

public class PhoneVariationBuilder extends Builder {
	
	private PhoneAsset variantPhone = new PhoneAsset();
	
	public void createPhoneVariations(PhoneAsset phone) {
		
		List<Variation> variationList = phone.getVariations();
		
		for (Variation v : variationList) {
			variantPhone.setId(v.getId());
			variantPhone.setOosThresholdOverride(v.getOosThresholdOverride());
			variantPhone.setMap(v.getMap());
			variantPhone.setColorVariant(v.getColorVariant());
			variantPhone.setGradientColor(v.getGradientColor());
			variantPhone.setMemoryVariant(v.getMemoryVariant());
			
		
			try {
				super.createPhoneAsset(variantPhone);
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}		
	}

}
