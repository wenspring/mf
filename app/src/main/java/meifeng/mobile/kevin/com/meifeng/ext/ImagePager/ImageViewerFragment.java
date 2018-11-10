package meifeng.mobile.kevin.com.meifeng.ext.ImagePager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import meifeng.mobile.kevin.com.meifeng.R;
import meifeng.mobile.kevin.com.meifeng.utils.file.FileUtils;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Kevin on 2018/7/12.
 */

public class ImageViewerFragment extends Fragment {
    private String mImageUrl;

    private ImageView iv_img;
    private ProgressBar pBar_loading;
    private PhotoViewAttacher mAttacher;

    public static ImageViewerFragment newInstance(String imageUrl) {

        ImageViewerFragment mFragment = new ImageViewerFragment();

        Bundle mArgs = new Bundle();
        mArgs.putString("url", imageUrl);
        mFragment.setArguments(mArgs);
        return mFragment;
    }

    public ImageViewerFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_view, container, false);

        iv_img = (ImageView) view.findViewById(R.id.image);
        pBar_loading = (ProgressBar) view.findViewById(R.id.loading);
        mAttacher = new PhotoViewAttacher(iv_img);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });

    //     mAttacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("imagepager",mImageUrl);
        if("gif".equalsIgnoreCase(FileUtils.getExt(mImageUrl))){

            Glide.with( getActivity() )
                    .load( mImageUrl )
                    .asGif() //判断加载的url资源是否为gif格式的资源
                    .error(R.drawable.ic_pic_loadfail)
                    .listener(new RequestListener<String, GifDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
                           Log.e("ImageViewerFragment",e.getMessage());

                            Glide.with(getActivity()).load(mImageUrl)
                                    .error(R.drawable.ic_pic_loadfail)
                                    .into(iv_img);
//                            if (getActivity() != null) {
//                                Toast.makeText(getActivity(), "图片加载失败!", Toast.LENGTH_SHORT).show();
//                            }
                            mAttacher.update();

                            pBar_loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (mAttacher != null) {
                                mAttacher.update();
                            } else {
                                mAttacher = new PhotoViewAttacher(iv_img);
                                mAttacher.update();
                            }
                            pBar_loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(iv_img);
        }else{
            Glide.with( getActivity() )
                    .load( mImageUrl )
                    .error(R.drawable.ic_pic_loadfail)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                            Glide.with(getActivity()).load(mImageUrl)
                                    .error(R.drawable.ic_pic_loadfail)
                                    .into(iv_img);
//                            if (getActivity() != null) {
//                                Toast.makeText(getActivity(), "图片加载失败!", Toast.LENGTH_SHORT).show();
//                            }

                            mAttacher.update();
                            pBar_loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            if (mAttacher != null) {
                                mAttacher.update();
                            } else {
                                mAttacher = new PhotoViewAttacher(iv_img);
                                mAttacher.update();
                            }
                            pBar_loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(iv_img);
        }



//        Picasso.with(getActivity()).load(mImageUrl).error(ContextCompat.getDrawable(getActivity(), R.drawable.img_error)).into(iv_img, new Callback() {
//
//            @Override
//            public void onSuccess() {
//                // TODO Auto-generated method stub
//
//                if (mAttacher != null) {
//                    mAttacher.update();
//                } else {
//                    mAttacher = new PhotoViewAttacher(iv_img);
//                    mAttacher.update();
//                }
//                pBar_loading.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onError() {
//                // TODO Auto-generated method stub
//                if (getActivity() != null) {
//                    Toast.makeText(getActivity(), "图片加载失败!", Toast.LENGTH_SHORT).show();
//                }
//                mAttacher.update();
//
//                pBar_loading.setVisibility(View.GONE);
//            }
//
//        });
    }
}
