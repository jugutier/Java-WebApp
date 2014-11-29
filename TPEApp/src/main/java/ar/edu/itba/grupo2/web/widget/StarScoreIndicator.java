package ar.edu.itba.grupo2.web.widget;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

@SuppressWarnings("serial")
public class StarScoreIndicator extends Panel {
	
	List<WebMarkupContainer> stars = new ArrayList<WebMarkupContainer>();

	public StarScoreIndicator(String id, int score) {
		super(id);
		
		RepeatingView starList = new RepeatingView("starList");
		
		// Fill list with stars
		for (int i = 0; i < 5; i++) {
			Fragment fragment = new Fragment (starList.newChildId(), "star", this);
			WebMarkupContainer container = new WebMarkupContainer("starIcon");
			fragment.add(container);
			starList.add(fragment);
			/*if (i < score) {
				starList.add(new Fragment (starList.newChildId(), "starFull", this));
			}
			else {
				starList.add(new Fragment (starList.newChildId(), "starEmpty", this));
			}*/
		}
		
		add(starList);
	}
}
