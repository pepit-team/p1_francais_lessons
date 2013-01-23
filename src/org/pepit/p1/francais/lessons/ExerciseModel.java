/**
 * @file org/pepit/p1/francais/lessons/ExerciseModel.java
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Vector;

import org.pepit.plugin.Utils;

import android.util.JsonReader;

public class ExerciseModel {

    public ExerciseModel(org.pepit.plugin.Interface plugin, String letter) {
	this.letter = letter;
	read(plugin);
    }

    public void build(int moduleNumber, int questionNumber) {
	Random r = new Random();
	Vector < Integer > cardList = new Vector < Integer >();
	
	for (int i = 0; i < cardNumber; ++i) {
	    cardList.add(Integer.valueOf(i));
	}
	cardIDs = new int[moduleNumber][questionNumber];
	for (int i = 0; i < moduleNumber; ++i) {
	    for (int j = 0; j < questionNumber; ++j) {
		int index = r.nextInt(cardList.size());
		
		cardIDs[i][j] = cardList.elementAt(index).intValue();
		cardList.remove(index);
	    }
	}
    }

    public int getCardID(int module, int question) {
	return cardIDs[module - 1][question - 1];
    }

    public String getLabel(int module, int question) {
	return labels[cardIDs[module - 1][question - 1] - 1];
    }

    public Response getResponse(int module, int question) {
	return responses[cardIDs[module - 1][question - 1] - 1];
    }

    private void read(org.pepit.plugin.Interface plugin) {
	InputStream in = null;
	JsonReader reader = null;

	try {
	    in = Utils.getDataFile(plugin, letter + ".json");
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try {
	    reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	try {
	    reader.beginObject();
	    while (reader.hasNext()) {
		String name = reader.nextName();

		if (name.equals("number")) {
		    cardNumber = (int) reader.nextLong();
		    responses = new Response[cardNumber];
		    labels = new String[cardNumber];
		} else if (name.equals("cards")) {
		    readCards(reader);
		}
	    }
	    reader.endObject();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private void readCards(JsonReader reader) {
	try {
	    reader.beginArray();
	    while (reader.hasNext()) {
		int id = 0;
		String text = "";
		Response response = Response.SEE_AND_HEAR;

		reader.beginObject();
		while (reader.hasNext()) {
		    String name = reader.nextName();

		    if (name.equals("id")) {
			id = (int) reader.nextLong();
		    } else if (name.equals("name")) {
			text = reader.nextString();
		    } else if (name.equals("response")) {
			long r = reader.nextLong();

			if (r == 1) {
			    response = Response.SEE_AND_HEAR;
			} else if (r == 2) {
			    response = Response.SEE_AND_NOT_HEAR;
			} else {
			    response = Response.NOT_SEE_AND_NOT_HEAR;
			}
		    } else {
			reader.skipValue();
		    }
		}
		responses[id - 1] = response;
		labels[id - 1] = text;
		reader.endObject();
	    }
	    reader.endArray();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private String letter;

    private int cardNumber;
    private int[][] cardIDs;

    private Response[] responses;
    private String[] labels;
}
