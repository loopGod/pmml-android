/*
 * Copyright (c) 2016 Villu Ruusmann
 *
 * This file is part of JPMML-Android
 *
 * JPMML-Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-Android.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.android;

import java.io.InputStream;

import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.SerializationUtil;

public class EvaluatorUtil {

	private EvaluatorUtil(){
	}

	static
	public Evaluator createEvaluator(InputStream is) throws Exception {
		PMML pmml = SerializationUtil.deserializePMML(is);

		ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();

		ModelEvaluator<?> modelEvaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
		modelEvaluator.verify();

		return modelEvaluator;
	}
}
