/*
 * Copyright (c) 2019 Villu Ruusmann
 *
 * This file is part of JPMML-SkLearn
 *
 * JPMML-SkLearn is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-SkLearn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-SkLearn.  If not, see <http://www.gnu.org/licenses/>.
 */
package sklearn2pmml.decoration;

import java.util.ArrayList;
import java.util.List;

import org.dmg.pmml.DataField;
import org.dmg.pmml.DataType;
import org.dmg.pmml.OpType;
import org.jpmml.converter.Feature;
import org.jpmml.converter.ObjectFeature;
import org.jpmml.converter.WildcardFeature;
import org.jpmml.sklearn.SkLearnEncoder;

abstract
public class TemporalDomain extends Domain {

	public TemporalDomain(String module, String name){
		super(module, name);
	}

	@Override
	public OpType getOpType(){
		return OpType.ORDINAL;
	}

	@Override
	public List<Feature> encodeFeatures(List<Feature> features, SkLearnEncoder encoder){
		List<Feature> result = new ArrayList<>();

		OpType opType = getOpType();
		DataType dataType = getDataType();

		for(Feature feature : features){
			WildcardFeature wildcardFeature = (WildcardFeature)feature;

			DataField dataField = (DataField)encoder.getField(wildcardFeature.getName());

			dataField
				.setOpType(opType)
				.setDataType(dataType);

			feature = new ObjectFeature(encoder, dataField.getName(), dataField.getDataType());

			result.add(feature);
		}

		return super.encodeFeatures(result, encoder);
	}
}