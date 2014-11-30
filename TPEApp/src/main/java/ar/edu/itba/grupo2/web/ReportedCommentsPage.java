package ar.edu.itba.grupo2.web;

import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.comment.CommentRepo;
import ar.edu.itba.grupo2.web.widget.comment.ReportedCommentListItem;

@SuppressWarnings("serial")
public class ReportedCommentsPage extends BasePage {
	
	@SpringBean
	private CommentRepo comments;
	
	public ReportedCommentsPage() {
		super();
		
		final IModel<List<Comment>> commentModel = new LoadableDetachableModel<List<Comment>>() {
			@Override
			protected List<Comment> load() {
				List<Comment> reported = comments.getAllReported();
				return reported;
			}
		};
		
		setDefaultModel(commentModel);
		
		PropertyListView<Comment> commentListView = new PropertyListView<Comment>("commentList", commentModel) {
			@Override
			protected void populateItem(ListItem<Comment> item) {
				item.add(new ReportedCommentListItem("commentListItem", item.getModel()) {
					@Override
					public void onDiscardReports() {
						super.onDiscardReports();
						commentModel.detach();
					}
				});	
			}
		};
		
		add(commentListView);
	}
}
