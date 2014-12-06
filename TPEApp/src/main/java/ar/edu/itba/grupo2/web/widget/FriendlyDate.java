package ar.edu.itba.grupo2.web.widget;

import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.ocpsoft.prettytime.PrettyTime;

@SuppressWarnings("serial")
public class FriendlyDate extends Panel {

	public FriendlyDate(String id) {
		super(id);
		init();
	}
	
	public FriendlyDate(String id, IModel<Date> date) {
		super(id, date);
		init();
	}
	
	private void init() {
		add(new Label("date", new AbstractReadOnlyModel<String>() {

			@Override
			public String getObject() {
				PrettyTime p = new PrettyTime();
				return p.format((Date) getDefaultModelObject()) + " (" + getDefaultModelObject() + ")";
			}
			
		}));
	}
}
