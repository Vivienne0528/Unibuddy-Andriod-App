// Generated by view binder compiler. Do not edit!
package com.example.unibody.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.unibody.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ChatLeftImageBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final CardView card;

  @NonNull
  public final ImageView headImage;

  @NonNull
  public final ImageView image;

  @NonNull
  public final TextView timeTv;

  private ChatLeftImageBinding(@NonNull RelativeLayout rootView, @NonNull CardView card,
      @NonNull ImageView headImage, @NonNull ImageView image, @NonNull TextView timeTv) {
    this.rootView = rootView;
    this.card = card;
    this.headImage = headImage;
    this.image = image;
    this.timeTv = timeTv;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ChatLeftImageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ChatLeftImageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.chat_left_image, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ChatLeftImageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.card;
      CardView card = ViewBindings.findChildViewById(rootView, id);
      if (card == null) {
        break missingId;
      }

      id = R.id.head_image;
      ImageView headImage = ViewBindings.findChildViewById(rootView, id);
      if (headImage == null) {
        break missingId;
      }

      id = R.id.image;
      ImageView image = ViewBindings.findChildViewById(rootView, id);
      if (image == null) {
        break missingId;
      }

      id = R.id.time_tv;
      TextView timeTv = ViewBindings.findChildViewById(rootView, id);
      if (timeTv == null) {
        break missingId;
      }

      return new ChatLeftImageBinding((RelativeLayout) rootView, card, headImage, image, timeTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
