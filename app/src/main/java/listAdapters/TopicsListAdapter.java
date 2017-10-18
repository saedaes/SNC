package listAdapters;

/**
 * Created by marco on 17/10/17.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import models.Topic;
import supind.corp.snc.R;
import java.util.ArrayList;

public class TopicsListAdapter extends ArrayAdapter<Topic> {
    public TopicsListAdapter(Context context, ArrayList<Topic> topics) {
        super(context, 0, topics);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Topic topic = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.topics_list_row, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.topicName);
        TextView description = (TextView) convertView.findViewById(R.id.topicDescription);
        name.setText(topic.name);
        description.setText(topic.description);
        return convertView;
    }
}
