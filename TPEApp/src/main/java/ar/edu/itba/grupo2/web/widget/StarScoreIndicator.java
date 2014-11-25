package ar.edu.itba.grupo2.web.widget;

import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

@SuppressWarnings("serial")
public class StarScoreIndicator extends Panel {

	public StarScoreIndicator(String id, int score, int maxScore) {
		super(id);
		
		RepeatingView starList = new RepeatingView("star-list");
		
		// Fill list with stars
		for (int i = 0; i < maxScore; i++) {
			if (i < score) {
				starList.add(new Fragment (starList.newChildId(), "star-full", this));
			}
			else {
				starList.add(new Fragment (starList.newChildId(), "star-empty", this));
			}
		}
		
		add(starList);
	}
}
