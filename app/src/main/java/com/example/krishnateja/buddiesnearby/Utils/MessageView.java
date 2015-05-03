package com.example.krishnateja.buddiesnearby.Utils;

import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.krishnateja.buddiesnearby.Activities.ChatActivity;
import com.example.krishnateja.buddiesnearby.R;
import com.layer.sdk.messaging.Message;
import com.layer.sdk.messaging.MessagePart;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by krishnateja on 5/2/2015.
 */
public class MessageView {
    //The parent object (in this case, a LinearLayout object with a ScrollView parent)
    private LinearLayout myParent;
    private String screen_name;
    //The sender and message views
    private TextView senderTV;
    private TextView messageTV;

    private ImageView statusImage;

    private LinearLayout messageDetails;

// reference to the chat activity
    private ChatActivity ma;

    //Takes the Layout parent object and message
    public MessageView(LinearLayout parent, Message msg, ChatActivity ma){
        myParent = parent;
        this.ma = ma;
        //The first part of each message will include the sender and status
        messageDetails = new LinearLayout(parent.getContext());

       //messageDetails.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.7f));
        messageDetails.setOrientation(LinearLayout.HORIZONTAL);
        myParent.addView(messageDetails);

        //Creates the sender text view, sets the text to be italic, and attaches it to the parent
        senderTV = new TextView(parent.getContext());
        senderTV.setTypeface(null, Typeface.ITALIC);
        messageDetails.addView(senderTV);

        //Creates the message text view and attaches it to the parent
        messageTV = new TextView(parent.getContext());
//        messageTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));//, 0.7f));
        myParent.addView(messageTV);

        //The status is displayed with an icon, depending on whether the message has been read, delivered, or sent
        //statusImage = new ImageView(parent.getContext());
        statusImage = createStatusImage(msg);//statusImage.setImageResource(R.drawable.sent);
        messageDetails.addView(statusImage);


        //Populates the text views
        UpdateMessage(msg);
    }

    //Takes a message and sets the text in the two text views
    public void UpdateMessage(Message msg){
        String senderTxt = craftSenderText(msg);
        String msgTxt = craftMsgText(msg);
        if(msg.getSentByUserId().equalsIgnoreCase(ma.getUserID())){
            messageDetails.setGravity(Gravity.LEFT);
            messageTV.setGravity(Gravity.LEFT);
        }
        else{
            messageDetails.setGravity(Gravity.RIGHT);
            messageTV.setGravity(Gravity.RIGHT);
        }
        senderTV.setText(senderTxt);
        messageTV.setText(msgTxt);
        //messageTV.setWidth(0);
        //senderTV.setWidth(0);
    }

    //The sender text is formatted like so:
    //  User @ Timestamp - Status
    private String craftSenderText(Message msg){

        //The User ID
        String senderTxt = msg.getConversation().getMetadata().containsKey("screen-name")?
                msg.getConversation().getMetadata().get("screen-name").toString():
                msg.getSentByUserId();
        try {
            Log.d("SENDING MESSAGE", msg.getConversation().getMetadata().get("screen-name").toString());
        }
        catch(Exception e){}
        //Add the timestamp
        if(msg.getSentAt() != null) {
            senderTxt += " @ " + new SimpleDateFormat("HH:mm:ss").format(msg.getReceivedAt());
        }

        //Add some formatting before the status icon
        senderTxt += "   ";

        //Return the formatted text
        return senderTxt;
    }

    //Checks the recipient status of the message (based on all participants)
    private Message.RecipientStatus getMessageStatus(Message msg) {

        //If we didn't send the message, we already know the status - we have read it
        if (ma != null && !msg.getSentByUserId().equalsIgnoreCase(ma.getUserID()))
            return Message.RecipientStatus.READ;

        //Assume the message has been sent
        Message.RecipientStatus status = Message.RecipientStatus.SENT;

        //Go through each user to check the status, in this case we check each user and prioritize so
        // that we return the highest status: Sent -> Delivered -> Read
        if(ma != null) {
            for (int i = 0; i < ma.getAllParticipants().size(); i++) {

                //Don't check the status of the current user
                String participant = ma.getAllParticipants().get(i);
                if (participant.equalsIgnoreCase(ma.getUserID()))
                    continue;

                if (status == Message.RecipientStatus.SENT) {

                    if (msg.getRecipientStatus(participant) == Message.RecipientStatus.DELIVERED)
                        status = Message.RecipientStatus.DELIVERED;

                    if (msg.getRecipientStatus(participant) == Message.RecipientStatus.READ)
                        return Message.RecipientStatus.READ;

                } else if (status == Message.RecipientStatus.DELIVERED) {
                    if (msg.getRecipientStatus(participant) == Message.RecipientStatus.READ)
                        return Message.RecipientStatus.READ;
                }
            }
        }
        return status;
    }

    //Checks the message parts and parses the message contents
    private String craftMsgText(Message msg){

        //The message text
        String msgText = "";

        //Go through each part, and if it is text (which it should be by default), append it to the
        // message text
        List<MessagePart> parts = msg.getMessageParts();
        for(int i = 0; i < msg.getMessageParts().size(); i++){

            //You can always set the mime type when creating a message part, by default the mime type
            // is initialized to plain text when the message part is created
            if(parts.get(i).getMimeType().equalsIgnoreCase("text/plain")) {
                try {
                    msgText += new String(parts.get(i).getData(), "UTF-8") + "\n";
                } catch (UnsupportedEncodingException e) {

                }
            }
        }

        //Return the assembled text
        return msgText;
    }

    //Sets the status image based on whether other users in the conversation have received or read
    //the message
    private ImageView createStatusImage(Message msg){
        ImageView status = new ImageView(myParent.getContext());

        switch(getMessageStatus(msg)){

            case SENT:
                status.setImageResource(R.drawable.sent);
                break;

            case DELIVERED:
                status.setImageResource(R.drawable.delivered);
                break;

            case READ:
                status.setImageResource(R.drawable.read);
                break;
        }

        //Have the icon fill the space vertically
        status.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        return status;
    }

}

