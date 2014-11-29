package ar.edu.itba.grupo2.web.widget;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

@SuppressWarnings("serial")
public class StarScoreIndicator extends Panel {
	
	List<WebMarkupContainer> stars = new ArrayList<WebMarkupContainer>();

	public StarScoreIndicator(String id, IModel<Integer> score) {
		super(id, score);
		
		RepeatingView starList = new RepeatingView("starList");
		
		// Fill list with stars
		for (int i = 0; i < 5; i++) {
			final int index = i;
			Fragment fragment = new Fragment (starList.newChildId(), "star", this);
			WebMarkupContainer container = new WebMarkupContainer("starIcon");
			AttributeModifier starClass = new AttributeModifier("class", new Model<String>() {
				
				@Override
				public String getObject() {
					String starType = "icon-star-empty";
					int starAmount = (int) Math.floor(((Number) StarScoreIndicator.this.getDefaultModelObject()).intValue());
					if (index < starAmount) {
						starType = "icon-star";
					}
					
					return starType;
				}
			});
			container.add(starClass);
			fragment.add(container);
			starList.add(fragment);
		}
		
		add(starList);
	}
	
	
}
