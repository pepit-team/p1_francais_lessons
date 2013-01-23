/**
 * @file org/pepit/p1/francais/lessons/ExerciseView.java
 * 
 * PepitMobil: an educational software
 * This file is a part of the PepitModel environment
 * http://pepit.be
 *
 * Copyright (C) 2012-2013 Pepit Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.pepit.p1.francais.lessons;

import java.io.IOException;

import org.pepit.plugin.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseView {

    public ExerciseView(Context ctx, org.pepit.plugin.Interface plugin,
	    int moduleNumber, int questionNumber) {
	this.plugin = plugin;
	this.moduleNumber = moduleNumber;
	this.questionNumber = questionNumber;
	this.context = ctx;
	
	init(ctx, moduleNumber, questionNumber);

	rootLayout = new LinearLayout(ctx);

	rootLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

	LinearLayout topLayout = new LinearLayout(ctx);
	LinearLayout.LayoutParams topLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT);

	topLayout.setOrientation(LinearLayout.HORIZONTAL);
	topLayout.setBackgroundColor(Color.WHITE);

	buildLeftLayout(topLayout);
	buildRightLayout(topLayout);

	rootLayout.addView(topLayout, topLayoutParams);
    }

    private ImageView buildButton(Bitmap bitmap, Response r) {
	ImageView button = new ImageView(context);
	final Response rfinal = r;

	button.setMaxHeight(100);
	button.setAdjustViewBounds(true);
	if (r == Response.SEE_AND_HEAR) {
	    button.setImageBitmap(seeAndHearBitmap);
	} else if (r == Response.SEE_AND_NOT_HEAR) {
	    button.setImageBitmap(seeAndNotHearBitmap);
	} else {
	    button.setImageBitmap(notSeeAndNotHearBitmap);
	}
	button.setPadding(5, 5, 5, 5);
	button.setBackgroundColor(Color.WHITE);
	button.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		v.setBackgroundColor(Color.RED);
		v.invalidate();
		setResponse(rfinal);
	    }
	});
	return button;
    }

    private void buildLeftLayout(LinearLayout layout) {
	LinearLayout leftLayout = new LinearLayout(context);
	LinearLayout.LayoutParams leftLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.MATCH_PARENT, 1);

	leftLayout.setOrientation(LinearLayout.VERTICAL);
	leftLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);
	leftLayout.setHorizontalGravity(Gravity.RIGHT);
	leftLayout.setPadding(0, 0, 10, 10);

	Bitmap bitmap = null;

	try {
	    bitmap = Utils.getImage(plugin,
		    "card_A_" + model.getCardID(moduleNumber, questionNumber)
			    + ".png");
	} catch (IOException e) {
	    e.printStackTrace();
	}

	seeAndHearButton = buildButton(bitmap, Response.SEE_AND_HEAR);
	seeAndNotHearButton = buildButton(bitmap, Response.SEE_AND_NOT_HEAR);
	notSeeAndNotHearButton = buildButton(bitmap,
		Response.NOT_SEE_AND_NOT_HEAR);

	leftLayout.addView(seeAndHearButton);
	leftLayout.addView(seeAndNotHearButton);
	leftLayout.addView(notSeeAndNotHearButton);

	layout.addView(leftLayout, leftLayoutParams);
    }

    private void buildRightLayout(LinearLayout layout) {
	Bitmap bitmap = null;

	try {
	    bitmap = Utils.getImage(plugin,
		    "card_A_" + model.getCardID(moduleNumber, questionNumber)
			    + ".png");
	} catch (IOException e) {
	    e.printStackTrace();
	}

	LinearLayout rightLayout = new LinearLayout(context);
	LinearLayout.LayoutParams rightLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT, 10);

	rightLayout.setOrientation(LinearLayout.VERTICAL);
	rightLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);
	rightLayout.setHorizontalGravity(Gravity.LEFT);

	cardView = new ImageView(context);
	cardView.setScaleType(ImageView.ScaleType.CENTER);
	cardView.setImageBitmap(bitmap);
	cardView.setPadding(1, 1, 1, 1);
	cardView.setBackgroundColor(Color.BLACK);

	word = new TextView(context);
	word.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
	word.setTextSize(30);
	word.setText(model.getLabel(moduleNumber, questionNumber));

	rightLayout.addView(cardView);
	rightLayout.addView(word);

	layout.addView(rightLayout, rightLayoutParams);
    }

    public boolean check() {
	return model.getResponse(moduleNumber, questionNumber) == response;
    }

    public LinearLayout getLayout() {
	return rootLayout;
    }

    private void init(Context context, int moduleNumber, int questionNumber) {
	model = new ExerciseModel(plugin, "A", cardNumber);
	model.build(moduleNumber, questionNumber);
	try {
	    seeAndHearBitmap = Utils.getImage(plugin, "image_SH.png");
	    seeAndNotHearBitmap = Utils.getImage(plugin, "image_SNH.png");
	    notSeeAndNotHearBitmap = Utils.getImage(plugin, "image_NSNH.png");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	response = Response.UNDEFINED;
    }

    private void setResponse(Response r) {
	response = r;
    }

    private final int cardNumber = 25;

    private int moduleNumber;
    private int questionNumber;

    private org.pepit.plugin.Interface plugin;

    private LinearLayout rootLayout;

    private Bitmap seeAndHearBitmap = null;
    private Bitmap seeAndNotHearBitmap = null;
    private Bitmap notSeeAndNotHearBitmap = null;

    private ImageView seeAndHearButton;
    private ImageView seeAndNotHearButton;
    private ImageView notSeeAndNotHearButton;

    private Context context;

    private TextView word;
    private ImageView cardView;

    private ExerciseModel model;

    private Response response;
}