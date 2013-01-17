/**
 * @file org/pepit/p1/francais/lessons/ExerciseActivity.java
 * 
 * PepitModel: an educational software
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

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	init(this);

	LinearLayout rootLayout = new LinearLayout(this);
	LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.MATCH_PARENT);

	rootLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

	LinearLayout topLayout = new LinearLayout(this);
	LinearLayout.LayoutParams topLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT);

	topLayout.setOrientation(LinearLayout.HORIZONTAL);
	topLayout.setBackgroundColor(Color.WHITE);

	buildLeftLayout(topLayout);
	buildRightLayout(topLayout);

	rootLayout.addView(topLayout, topLayoutParams);

	setContentView(rootLayout, rootLayoutParams);
    }

    private ImageView buildButton(Bitmap bitmap, Response r) {
	ImageView button = new ImageView(this);
	final Response rfinal = r;

	button.setMaxHeight(100);
	button.setAdjustViewBounds(true);
	button.setImageBitmap(bitmap);
	button.setPadding(1, 1, 1, 1);
	button.setBackgroundColor(Color.BLACK);
	button.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		checkAnswer(rfinal);
	    }
	});
	return button;
    }

    private void buildLeftLayout(LinearLayout layout) {
	LinearLayout leftLayout = new LinearLayout(this);
	LinearLayout.LayoutParams leftLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.MATCH_PARENT, 1);

	leftLayout.setOrientation(LinearLayout.VERTICAL);
	leftLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);
	leftLayout.setHorizontalGravity(Gravity.RIGHT);
	leftLayout.setPadding(0, 0, 10, 0);

	Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		resourceIDs[0]);

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
	Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		resourceIDs[0]);

	LinearLayout rightLayout = new LinearLayout(this);
	LinearLayout.LayoutParams rightLayoutParams = new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT, 10);

	rightLayout.setOrientation(LinearLayout.VERTICAL);
	rightLayout.setVerticalGravity(Gravity.CENTER_VERTICAL);
	rightLayout.setHorizontalGravity(Gravity.LEFT);

	cardView = new ImageView(this);
	cardView.setScaleType(ImageView.ScaleType.CENTER);
	cardView.setImageBitmap(bitmap);
	cardView.setPadding(1, 1, 1, 1);
	cardView.setBackgroundColor(Color.BLACK);

	word = new TextView(this);
	word.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
	word.setTextSize(30);
	word.setText("Le chameau");

	rightLayout.addView(cardView);
	rightLayout.addView(word);

	layout.addView(rightLayout, rightLayoutParams);
    }

    private void checkAnswer(Response r) {

    }

    private void init(Context context) {
	resourceIDs = new int[cardNumber];
	for (int i = 1; i <= cardNumber; ++i) {
	    String uri_start = "drawable/card_" + i;
	    int id = getResources().getIdentifier(uri_start + "_0", null,
		    context.getPackageName());

	    if (id == 0) {
		id = getResources().getIdentifier(uri_start + "_1", null,
			context.getPackageName());
		if (id == 0) {
		    id = getResources().getIdentifier(uri_start + "_2", null,
			    context.getPackageName());

		}
	    }
	    resourceIDs[i - 1] = id;
	}
    }

    private final int cardNumber = 10;

    private ImageView seeAndHearButton;
    private ImageView seeAndNotHearButton;
    private ImageView notSeeAndNotHearButton;

    private TextView word;
    private ImageView cardView;

    int resourceIDs[];
}