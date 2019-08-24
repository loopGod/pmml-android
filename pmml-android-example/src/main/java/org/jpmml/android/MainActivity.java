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
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelField;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button loadButton = (Button)findViewById(R.id.loadButton);

		View.OnClickListener onClickListener = new View.OnClickListener(){

			@Override
			public void onClick(View view){
				Evaluator evaluator;

				try {
					evaluator = createEvaluator();
				} catch(Exception e){
					throw new RuntimeException(e);
				}

				showEvaluatorDialog(evaluator);
			}
		};
		loadButton.setOnClickListener(onClickListener);
	}

	@Override
	public void onStop(){
		super.onStop();
	}

	private void showEvaluatorDialog(Evaluator evaluator){
		List<FieldName> activeFields = getNames(evaluator.getActiveFields());
		List<FieldName> targetFields = getNames(evaluator.getTargetFields());
		List<FieldName> outputFields = getNames(evaluator.getOutputFields());

		StringBuilder sb = new StringBuilder();

		sb.append("Active fields: ").append(activeFields);
		sb.append("\n");
		sb.append("Target fields: ").append(targetFields);
		sb.append("\n");
		sb.append("Output fields: ").append(outputFields);

		TextView textView = new TextView(this);
		textView.setText(sb.toString());

		Dialog dialog = new Dialog(this);
		dialog.setContentView(textView);
		dialog.show();
	}

	private Evaluator createEvaluator() throws Exception {
		AssetManager assetManager = getAssets();

		try(InputStream is = assetManager.open("model.pmml.ser")){
			return EvaluatorUtil.createEvaluator(is);
		}
	}

	static
	private List<FieldName> getNames(List<? extends ModelField> modelFields){
		List<FieldName> names = new ArrayList<>(modelFields.size());

		for(ModelField modelField : modelFields){
			FieldName name = modelField.getName();

			names.add(name);
		}

		return names;
	}
}