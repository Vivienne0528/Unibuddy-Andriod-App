// Generated by view binder compiler. Do not edit!
package com.example.unibody.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.unibody.R;
import com.example.unibody.me.fragment.view.CustomRoundImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemPhotoBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView ivClose;

  @NonNull
  public final ImageView ivFailed;

  @NonNull
  public final CustomRoundImageView ivPhoto;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final View mask;

  private ItemPhotoBinding(@NonNull LinearLayout rootView, @NonNull ImageView ivClose,
      @NonNull ImageView ivFailed, @NonNull CustomRoundImageView ivPhoto,
      @NonNull ProgressBar loading, @NonNull View mask) {
    this.rootView = rootView;
    this.ivClose = ivClose;
    this.ivFailed = ivFailed;
    this.ivPhoto = ivPhoto;
    this.loading = loading;
    this.mask = mask;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemPhotoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemPhotoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_photo_, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemPhotoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_close;
      ImageView ivClose = ViewBindings.findChildViewById(rootView, id);
      if (ivClose == null) {
        break missingId;
      }

      id = R.id.iv_failed;
      ImageView ivFailed = ViewBindings.findChildViewById(rootView, id);
      if (ivFailed == null) {
        break missingId;
      }

      id = R.id.iv_photo;
      CustomRoundImageView ivPhoto = ViewBindings.findChildViewById(rootView, id);
      if (ivPhoto == null) {
        break missingId;
      }

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.mask;
      View mask = ViewBindings.findChildViewById(rootView, id);
      if (mask == null) {
        break missingId;
      }

      return new ItemPhotoBinding((LinearLayout) rootView, ivClose, ivFailed, ivPhoto, loading,
          mask);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
