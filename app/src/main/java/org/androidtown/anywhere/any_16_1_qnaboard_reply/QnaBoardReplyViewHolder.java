package org.androidtown.anywhere.any_16_1_qnaboard_reply;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidtown.anywhere.R;

import info.hoang8f.widget.FButton;

/**
 * Created by user on 2017-07-17.
 */

public class QnaBoardReplyViewHolder extends RecyclerView.ViewHolder {

    TextView qnaBoard_reply_id;
    TextView qnaBoard_reply_date;
    EditText qnaBoard_reply_content;

    LinearLayout customFrame;
    LinearLayout replyControlLayout;

    ImageButton inflateReplyController;

    /*댓글*/

    TextView qnaBoard_reply_lev;
    LinearLayout qnaBoard_reply_Info;

    FButton qnaBoard_reply_modfiy;
    FButton qnaBoard_reply_delete;
    FButton qnaBoard_reply_append;

    LinearLayout qnaBoard_reply_append_controll;
    EditText qnaBoard_reply_append_edit;
    FButton qnaBoard_reply_append_submit;

    LinearLayout qnaBoard_reply_modfiy_controll;
    EditText qnaBoard_reply_modfiy_edit;
    FButton qnaBoard_reply_modfiy_submit;


    public QnaBoardReplyViewHolder(View itemView) {
        super(itemView);

        qnaBoard_reply_id = (TextView) itemView.findViewById(R.id.qnaBoard_reply_id);
        qnaBoard_reply_date = (TextView) itemView.findViewById(R.id.qnaBoard_reply_date);
        qnaBoard_reply_content = (EditText) itemView.findViewById(R.id.qnaBoard_reply_content);
        customFrame = (LinearLayout) itemView.findViewById(R.id.board_detail_reply_custom_Frame);
        qnaBoard_reply_Info = (LinearLayout) itemView.findViewById(R.id.qnaBoard_reply_Info);

        qnaBoard_reply_modfiy = (FButton) itemView.findViewById(R.id.qnaBoard_reply_modfiy);
        qnaBoard_reply_delete = (FButton) itemView.findViewById(R.id.qnaBoard_reply_delete);
        qnaBoard_reply_append = (FButton) itemView.findViewById(R.id.qnaBoard_reply_append);

        qnaBoard_reply_append_controll = (LinearLayout) itemView.findViewById(R.id.qnaBoard_reply_append_controll);
        qnaBoard_reply_append_edit = (EditText) itemView.findViewById(R.id.qnaBoard_reply_append_edit);
        qnaBoard_reply_append_submit = (FButton) itemView.findViewById(R.id.qnaBoard_reply_append_submit);

        qnaBoard_reply_modfiy_controll = (LinearLayout) itemView.findViewById(R.id.qnaBoard_reply_modfiy_controll);
        qnaBoard_reply_modfiy_edit = (EditText) itemView.findViewById(R.id.qnaBoard_reply_modfiy_edit);
        qnaBoard_reply_modfiy_submit = (FButton) itemView.findViewById(R.id.qnaBoard_reply_modfiy_submit);

        replyControlLayout = (LinearLayout) itemView.findViewById(R.id.replyControlLayout);
        inflateReplyController = (ImageButton) itemView.findViewById(R.id.inflateReplyController);
    }


}
