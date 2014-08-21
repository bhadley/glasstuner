/** Copyright (C) 2009 by Aleksey Surkov.
 **
 ** Permission to use, copy, modify, and distribute this software and its
 ** documentation for any purpose and without fee is hereby granted, provided
 ** that the above copyright notice appear in all copies and that both that
 ** copyright notice and this permission notice appear in supporting
 ** documentation.  This software is provided "as is" without express or
 ** implied warranty.
 *
 * Modified by Beth Hadley to implement a simple "tuner"
 */

package com.example.AndroidTuner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Map.Entry;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import com.example.AndroidTuner.PitchDetectionRepresentation;

public class DrawableView extends View {

	private HashMap<Double, Double> frequencies_;
	private double pitch_;
	private PitchDetectionRepresentation representation_;
	private Handler handler_;
	private Timer timer_;
    private int NOTESDIM = 11;
	public DrawableView(Context context) {
		super(context);
		// NotePitches[i][j] is the pitch of i-th string on j-th fret. 0th fret means an open fret.
	
		Notes[0][0] = "E2";
		Notes[0][1] = "F2";
		Notes[0][2] = "F#2";
		Notes[0][3] = "G2";
		Notes[0][4] = "G#2";
		Notes[1][0] = "A2";
		Notes[1][1] = "A#2";
		Notes[1][2] = "B2";
		Notes[1][3] = "C3";
		Notes[1][4] = "C#3";
		Notes[2][0] = "D3";
		Notes[2][1] = "D#3";
		Notes[2][2] = "E3";
		Notes[2][3] = "F3";
		Notes[2][4] = "F#3";
		Notes[3][0] = "G3";
		Notes[3][1] = "G#3";
		Notes[3][2] = "A3";
		Notes[3][3] = "A#3";
		Notes[4][0] = "B3";
		Notes[4][1] = "C4";
		Notes[4][2] = "C#4";
		Notes[4][3] = "D4";
		Notes[4][4] = "D#4";
		Notes[5][0] = "E4";
		Notes[5][1] = "F4";
		Notes[5][2] = "F#4";
		Notes[5][3] = "G4";
		Notes[5][4] = "G#4";
		Notes[5][5] = "A4";
		Notes[6][0] = "A#4"; 
		Notes[6][1] = "B4"; 
		Notes[6][2] = "C5"; 
		Notes[6][3] = "C#5"; 
		Notes[6][4] = "D5"; 
		Notes[6][5] = "D#5"; 
		Notes[6][0] = "A#4"; 
		Notes[6][1] = "B4"; 
		Notes[6][2] = "C5"; 
		Notes[6][3] = "C#5"; 
		Notes[6][4] = "D5"; 
		Notes[6][5] = "D#5"; 
		Notes[7][0] = "E5"; 
		Notes[7][1] = "F5"; 
		Notes[7][2] = "F#5"; 
		Notes[7][3] = "G5"; 
		Notes[7][4] = "G#5"; 
		Notes[7][5] = "A5"; 
		Notes[8][0] = "A#5"; 
		Notes[8][1] = "B5"; 
		Notes[8][2] = "C6"; 
		Notes[8][3] = "C#6"; 
		Notes[8][4] = "D6"; 
		Notes[8][5] = "D#6"; 
		Notes[9][0] = "E6"; 
		Notes[9][1] = "F6"; 
		Notes[9][2] = "F#6"; 
		Notes[9][3] = "G6"; 
		Notes[9][4] = "G#6"; 
		Notes[9][5] = "A6"; 
		Notes[10][0] = "A#6"; 
		Notes[10][1] = "B6"; 
		Notes[10][2] = "C7"; 
		Notes[10][3] = "C#7"; 
		Notes[10][4] = "D7"; 
		Notes[10][5] = "D#7"; 
		
		NotePitches[0][0] = 82.41; 
		NotePitches[0][1] = 87.31; 
		NotePitches[0][2] = 92.5;  
		NotePitches[0][3] = 98;	
		NotePitches[0][4] = 103.8;	
		NotePitches[1][0] = 110;	
		NotePitches[1][1] = 116.54;	
		NotePitches[1][2] = 123.48;	
		NotePitches[1][3] = 130.82; 
		NotePitches[1][4] = 138.59; 
		NotePitches[2][0] = 147.83; 
		NotePitches[2][1] = 155.56; 
		NotePitches[2][2] = 164.81; 
		NotePitches[2][3] = 174.62; 
		NotePitches[2][4] = 185;  
		NotePitches[3][0] = 196;  
		NotePitches[3][1] = 207; 
		NotePitches[3][2] = 220; 
		NotePitches[3][3] = 233.08; 
		NotePitches[4][0] = 246.96; 
		NotePitches[4][1] = 261.63; 
		NotePitches[4][2] = 277.18; 
		NotePitches[4][3] = 293.66; 
		NotePitches[4][4] = 311.13; 
		NotePitches[5][0] = 329.63; 
		NotePitches[5][1] = 349.23; 
		NotePitches[5][2] = 369.99; 
		NotePitches[5][3] = 392; 
		NotePitches[5][4] = 415.3; 
		NotePitches[5][5] = 440; 
		NotePitches[6][0] = 466; 
		NotePitches[6][1] = 494; 
		NotePitches[6][2] = 523; 
		NotePitches[6][3] = 554; 
		NotePitches[6][4] = 587; 
		NotePitches[6][5] = 622;
		NotePitches[7][0] = 659; 
		NotePitches[7][1] = 699; 
		NotePitches[7][2] = 740; 
		NotePitches[7][3] = 784; 
		NotePitches[7][4] = 831; 
		NotePitches[7][5] = 880; 
		NotePitches[8][0] = 932; 
		NotePitches[8][1] = 988; 
		NotePitches[8][2] = 1047; 
		NotePitches[8][3] = 1109; 
		NotePitches[8][4] = 1175; 
		NotePitches[8][5] = 1245; 
		NotePitches[9][0] = 1319; 
		NotePitches[9][1] = 1397; 
		NotePitches[9][2] = 1475; 
		NotePitches[9][3] = 1568; 
		NotePitches[9][4] = 1661; 
		NotePitches[9][5] = 1760; 
		NotePitches[10][0] = 1865; 
		NotePitches[10][1] = 1976; 
		NotePitches[10][2] = 2093; 
		NotePitches[10][3] = 2218; 
		NotePitches[10][4] = 2349; 
		NotePitches[10][5] = 2489; 
		
		for (int string_no = 0; string_no < NOTESDIM; string_no++) {
			for (int fret = 0; fret < NOTESDIM; fret++) {
				if (NotePitches[string_no][fret] > 0) {
				  NotePitchesMap.put(NotePitches[string_no][fret], string_no * 100 + fret);  // encode coordinates
				}
			}
		}
		
		// UI update cycle.
		handler_ = new Handler();
		timer_ = new Timer();
		timer_.schedule(new TimerTask() {
				public void run() {
					handler_.post(new Runnable() {
						public void run() {
							invalidate();
						}
					});
				}
			},
			UI_UPDATE_MS ,
			UI_UPDATE_MS );
	}
	
	// NotePitches[i][j] is the pitch of i-th string on j-th fret. 0th fret means an open fret.
	private double[][] NotePitches = new double[NOTESDIM][NOTESDIM]; 
	private String[][] Notes = new String[NOTESDIM][NOTESDIM]; 
	private TreeMap<Double, Integer> NotePitchesMap = new TreeMap<Double, Integer>(); 
    
	private final static int MIN_AMPLITUDE = 40000;
	private final static int MAX_AMPLITUDE = 3200000;
	private final static double MAX_PITCH_DIFF = 20;  // in Hz
	private final static int UI_UPDATE_MS = 100;

	private int GetFingerboardCoord(double pitch) {
		final SortedMap<Double, Integer> tail_map = NotePitchesMap.tailMap(pitch);
		final SortedMap<Double, Integer> head_map = NotePitchesMap.headMap(pitch);
		final double closest_right = tail_map == null || tail_map.isEmpty() ? NotePitchesMap.lastKey() : tail_map.firstKey(); 
		final double closest_left = head_map == null || head_map.isEmpty() ? NotePitchesMap.firstKey() : head_map.lastKey();
		if (closest_right - pitch < pitch - closest_left) {
			return NotePitchesMap.get(closest_right);
		} else {
			return NotePitchesMap.get(closest_left);
		}
	}
	
	final int FINGERBOARD_PADDING = 0;
	final static int HEADSTOCK_HEIGHT = 1;
	final static int HEADSTOCK_WIDTH = 1;
	private void DrawFingerboard(Canvas canvas, Rect rect) {
		Paint paint = new Paint();
		paint.setARGB(255, 100, 200, 100);
        // Draw strings		
		for (int i = 0; i < 6; i++) {
			final int offset = Math.round((rect.height() - FINGERBOARD_PADDING * 2) / 5 * i) + FINGERBOARD_PADDING;
			canvas.drawLine(rect.left, rect.top + offset, rect.right, rect.top + offset, paint);
		}
		// Draw fingerboard's end.
		canvas.drawRect(rect.right - FINGERBOARD_PADDING, rect.top, rect.right, rect.bottom, paint);
		
        // Draw frets
		for (int i = 1; i < 6; i++) {
			final int offset = Math.round((rect.width() - FINGERBOARD_PADDING * 2) / 5 * i) + FINGERBOARD_PADDING;
			canvas.drawLine(rect.right - offset, rect.top, rect.right - offset, rect.bottom, paint);
		}

		// Draw guitar
		paint.setARGB(255, 195, 118, 27);  // a nice guitar color
		canvas.drawLine(rect.left, rect.top, rect.right, rect.top, paint);
		canvas.drawLine(rect.left, rect.bottom, rect.right, rect.bottom, paint);
		canvas.drawLine(rect.right + HEADSTOCK_WIDTH, rect.top - HEADSTOCK_HEIGHT, rect.right, rect.top, paint);
		canvas.drawLine(rect.right + HEADSTOCK_WIDTH, rect.bottom + HEADSTOCK_HEIGHT, rect.right, rect.bottom, paint);
		
		// Marks on the 3rd and 5th frets.
		final long offset_3_mark = Math.round((rect.width() - FINGERBOARD_PADDING * 2) / 5 * 2.5) + FINGERBOARD_PADDING;
		final long offset_5_mark = Math.round((rect.width() - FINGERBOARD_PADDING * 2) / 5 * 4.5) + FINGERBOARD_PADDING;
		canvas.drawCircle(rect.right - offset_3_mark, rect.top, 3, paint);
		canvas.drawCircle(rect.right - offset_5_mark, rect.top, 3, paint);
		
		
		// Draw strings on the headstock
		paint.setARGB(255, 100, 200, 100);
		for (int i = 1; i <= 6; i++) {
			canvas.drawLine(rect.right + HEADSTOCK_WIDTH,
					        rect.top - HEADSTOCK_HEIGHT + 
				            Math.round((rect.height() + 2 * HEADSTOCK_HEIGHT - FINGERBOARD_PADDING * 2) 
				                / 5 * (i - 1)) + FINGERBOARD_PADDING,
					        rect.right,
					        rect.top + 
					            Math.round((rect.height() - FINGERBOARD_PADDING * 2) / 5 * (i - 1)) + FINGERBOARD_PADDING,
					        paint);
		}
	}
	
	private long GetAmplitudeScreenHeight(Canvas canvas, double amplitude, Rect histogram_rect) {
		return Math.round(amplitude / MAX_AMPLITUDE * histogram_rect.height());
	}
	
	private void DrawPitchOnFingerboard(Canvas canvas, Rect rect, Point text_point) {
		final int MARK_RADIUS = 5;
		if (representation_ == null || !representation_.string_detected) {
			return;
		}
		
		final int alpha = representation_.GetAlpha();
		
		if (alpha == 0) {
			return;
		}
		
		int string_no = representation_.string_no;
		int fret = representation_.fret;
		
		Paint paint = new Paint();
		paint.setARGB(alpha, 200, 210, 210);
		if (fret == 0) {
			// Highlight the string.
			final int offset = Math.round((rect.height() - FINGERBOARD_PADDING * 2) / 5 * string_no) + FINGERBOARD_PADDING;
			canvas.drawLine(rect.left, rect.top + offset, rect.right, rect.top + offset, paint);
			// Actually use the corresponding coordinate on the previous string.
			if (string_no > 0) {
				if (string_no == 4) {
					fret = 4;
				} else {
					fret = 5;
				} 
				string_no--;
			}
		}
		
		// Draw the needed position on the fingerboard.
		final long offset_y = Math.round((rect.height() - FINGERBOARD_PADDING * 2) / 5 * string_no) + FINGERBOARD_PADDING;
		final long offset_x = Math.round((rect.width() - FINGERBOARD_PADDING * 2) 
				/ 5 * (fret - 0.5)) + FINGERBOARD_PADDING;
		final long circle_x = rect.right - offset_x;
		final long circle_y = rect.top + offset_y;
		canvas.drawCircle(circle_x, circle_y, MARK_RADIUS, paint);
		
		// Draw the position's pitch and the delta.
		paint.setARGB(alpha, 180, 180, 180);
		canvas.drawLine(text_point.x, text_point.y, text_point.x + 20, text_point.y, paint);
		canvas.drawLine(text_point.x, text_point.y, circle_x, circle_y, paint);
		paint.setTextSize(25);
		final double position_pitch = NotePitches[representation_.string_no][representation_.fret];
		final double delta = representation_.pitch - position_pitch;
		String message = position_pitch + " Hz (";
		message += delta > 0 ? "-" : "+";
		message += Math.round(Math.abs(delta) * 100) / 100.0 + "Hz)"; 
		canvas.drawText(message, text_point.x + 30, text_point.y + 10, paint);
		
	}

	private boolean DrawHistogram(Canvas canvas, Rect rect) {
		if (frequencies_ == null) return false;
		Paint paint = new Paint();
		// Draw border.
		paint.setARGB(80, 200, 200, 200);
		paint.setStyle(Paint.Style.STROKE);
		//canvas.drawRect(rect, paint);
		
		// Draw threshold.
		paint.setARGB(180, 200, 0, 0);
		final long threshold_screen_height = GetAmplitudeScreenHeight(canvas, MIN_AMPLITUDE, rect);
		canvas.drawLine(rect.left, rect.bottom - threshold_screen_height, rect.right, rect.bottom - threshold_screen_height, paint);

		// Draw histogram.
		paint.setARGB(255, 140, 140, 140);

		boolean above_threshold = false;
		int column_no = 0;
		Iterator<Entry<Double, Double>> it = frequencies_.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Double, Double> entry = it.next();
			// double frequency = entry.getKey();
			final double amplitude = Math.min(entry.getValue(), MAX_AMPLITUDE);
			final long height = GetAmplitudeScreenHeight(canvas, amplitude, rect);
			if (amplitude > MIN_AMPLITUDE) {
				above_threshold = true;
			}
			canvas.drawRect(
					rect.left + rect.width() * column_no / frequencies_.size(),
					rect.bottom - height, 
					rect.left + rect.width() * (column_no + 1) / frequencies_.size(),
					rect.bottom, 
					paint);
			column_no++;
		}
		return above_threshold;
	}

	private void DrawCurrentFrequency(Canvas canvas, int x, int y) {
		if (representation_ == null) {
			Paint paint = new Paint();
			paint.setARGB(255, 200, 200, 200);
			paint.setTextSize(18);
			canvas.drawText("Play pitch.", 20, 40, paint);
			return;
		}
		final int alpha = representation_.GetAlpha();
		if (alpha == 0) return;
		Paint paint = new Paint();
		paint.setARGB(alpha, 200, 0, 0);
		paint.setColor(Color.WHITE);
		paint.setTextSize(25);
		
		final double position_pitch = NotePitches[representation_.string_no][representation_.fret];
		
		canvas.drawText(Math.round(representation_.pitch * 10)/10.0 + "Hz", 300, 180, paint);
		
		final double delta = representation_.pitch - position_pitch;
		String note = Notes[representation_.string_no][representation_.fret];
		paint.setTextSize(35);
		canvas.drawText(note, 320, 80, paint);
		
		paint.setTextSize(25);
		String tonation = delta > 0 ? "+" : "-";
		tonation += Math.round(Math.abs(delta) * 100) / 100.0 + "Hz"; 
		canvas.drawText(tonation, 300, 120, paint);
		
		
		paint.setARGB(alpha, 200, 0, 0);
		paint.setTextSize(25);
		
		//Log.i("BETH", "actualPitch"+representation_.pitch);
		//Log.i("BETH", "delta:"+delta);

		
		float r = 100;
		Log.i("BETH", "delta"+ delta);
		float t = (float) ((35 * Math.abs(delta)) / 5);
		if (delta < 0) { // it's high
			t = 90 + t;
		}
		else {
			t = 90 -t;
		}
		if (t < 0) {t=0;}
		if (t>180) {t=180;}
		
		Log.i("BETH", "Degree: " + t);
		
		float h = 150;
		float k = 120;
		
		RectF rectf = new RectF(h-r, k-r, h+r, k*2);
		//canvas.drawRect(rectf, paint);
		paint.setARGB(255, 255, 255, 204);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawArc(rectf, 180, 180, true,  paint);
		
		float xPlus20 = h + (float) (r * Math.cos((55 * Math.PI) / 180) );
		float yPlus20 = k - (float) (r * Math.sin((55 * Math.PI) / 180) );
		paint.setTextSize(15);
		canvas.drawText("+20C", xPlus20, yPlus20, paint);
		
		float xMinus20 = h + (float) (r * Math.cos((125 * Math.PI) / 180) );
		float yMinus20 = k - (float) (r * Math.sin((125 * Math.PI) / 180) );
		canvas.drawText("-20C", xMinus20-35, yMinus20, paint);
		//canvas.drawText("-20C", 300, 120, paint);
		
		
		float xnew = h + (float) (r * Math.cos((t * Math.PI) / 180) );
		float ynew = k - (float) (r * Math.sin((t * Math.PI) / 180) );
		//Log.i("BETH",h+" " + k + " " + xnew + " " + ynew);
		paint.setStrokeWidth(7);
		paint.setStrokeCap(Cap.ROUND);
		canvas.drawLine(h, k, xnew, ynew, paint);
		//canvas.drawLine(320, 230, 320, 50, paint);
        
		
		//canvas.drawArc(rectf, 180, 200, false,  paint);
		//canvas.drawText(Math.round(representation_.pitch * 10) / 10.0 + " Hz", 20, 40, paint);
	}
	
	protected void onDraw(Canvas canvas) {
		final int MARGIN = 50;

		final int effective_height = canvas.getHeight() - 4 * MARGIN;
		final int effective_width = canvas.getWidth() - 2 * MARGIN;
		/*
		final Rect fingerboard = new Rect(MARGIN, 
				                          effective_height * 20 / 100 + MARGIN + HEADSTOCK_HEIGHT,
				                          effective_width + MARGIN - HEADSTOCK_WIDTH, 
				                          effective_height * 60 / 100 + MARGIN - HEADSTOCK_HEIGHT);
		final Rect histogram = new Rect(MARGIN, effective_height * 60 / 100 + 2 * MARGIN,
		*/
		final Rect histogram = new Rect(20,20,300,230);
              //  effective_width + MARGIN, effective_height + MARGIN);
		
		if (DrawHistogram(canvas, histogram)) {
			final int coord = GetFingerboardCoord(pitch_);
			final int string_no = coord / 100;
			final int fret = coord % 100;
			final double found_pitch = NotePitches[string_no][fret];
			final double diff = Math.abs(found_pitch - pitch_);
			if (diff < MAX_PITCH_DIFF) {
				representation_ = new PitchDetectionRepresentation(pitch_, string_no, fret);
			} else {
				representation_ = new PitchDetectionRepresentation(pitch_);
			}
		}
		
		DrawCurrentFrequency(canvas, 20, 50);
		//DrawFingerboard(canvas, fingerboard);
		//DrawPitchOnFingerboard(canvas, fingerboard, new Point(20, 80));
	}

	public void setDetectionResults(final HashMap<Double, Double> frequencies, double pitch) {
		frequencies_ = frequencies;
		pitch_ = pitch;
	}

}