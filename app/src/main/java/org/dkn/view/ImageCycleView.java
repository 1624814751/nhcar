package org.dkn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nhcar.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  <Pre>
 * �Զ���ͼƬ�Զ��ֲ��ؼ����Զ��ֲ�ָʾ����ʽ��֧�ֵ���������ֲ�����������ͼƬ</br>
 * ����ʹ��XUtil��BitmapUtilsҲ����ʹ��smart-image-view����ͼƬ��֧���ֲ������л�</br>
 * �˲���ǻ���viewpagerʵ�ֵ�,��Ҫ����android-support-v4.jar</br></br>
 *
 * ���ʹ������ͼƬ�ǵü�Ȩ�ޡ�</br>
 * uses-permission android:name="android.permission.INTERNET"
 * uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
 *
 * ��Ҫ����:</br></br>
 * 1.֧������������ʾ</br>
 * 2.֧���޸��ֲ�ָʾ������ʽ��λ�ã��޸�view_cycle_image.xml��ʽ,�����޸�id��</br>
 * 3.֧���޸���������ʽ��λ�ã��޸�view_cycle_image.xml��ʽ,�����޸�id��</br>
 * 4.֧�������Ƿ����Զ��ֲ�</br>
 * 5.֧��������������ֹͣ�Զ��ֲ�</br>
 * 6.֧���������ͼƬ����ԴͼƬid��sd��ͼƬ</br>
 * 7.����֧��XUtil��BitmapUtilsҲ����ʹ��smart-image-view����ͼƬ</br>
 * 8.֧�ֵ���¼�</br>
 * 9.Ĭ���ǵ�һ��</br></br>
 *
 * demoʵ��:</br> </br>
 *
 List<ImageCycleView.ImageInfo> list=new ArrayList<ImageCycleView.ImageInfo>();
 mImageCycleView = (ImageCycleView) findViewById(R.id.icv_topView);

 //��������ͼƬ�ͱ���ͼƬ���ʹ�ã�����Ҫ�����ImageCycleView.LoadImageCallBack�ص����жϴ���
 //�ж������ּ��ر���ͼƬ����http�����������ͼƬ
 List<ImageCycleView.ImageInfo> list=new ArrayList<ImageCycleView.ImageInfo>();

 //ʹ�ñ���ͼƬ
 //list.add(new ImageCycleView.ImageInfo(R.drawable.a1,"111111111111",""));
 //list.add(new ImageCycleView.ImageInfo(R.drawable.a2,"222222222222222",""));
 //list.add(new ImageCycleView.ImageInfo(R.drawable.a3,"3333333333333",""));

 //SD��ͼƬ��Դ
 list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a1.jpg"),"11111",""));
 list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a2.jpg"),"22222",""));
 list.add(new ImageCycleView.ImageInfo(new File(Environment.getExternalStorageDirectory(),"a3.jpg"),"33333",""));

 //ʹ���������ͼƬ
 list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/57ab6dc2-43f2-4087-81e2-b5ab5681642d.jpg","3333333333333",""));
 list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/cb56a1a6-6c33-41e4-9c3c-363f4ec6b728.jpg","222222222222222",""));
 list.add(new ImageCycleView.ImageInfo("http://img.lakalaec.com/ad/e4229e25-3906-4049-9fe8-e2b52a98f6d1.jpg","3333333333333",""));


 mImageCycleView.loadData(list, new ImageCycleView.LoadImageCallBack() {
@Override
public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo){

//����ͼƬ
//ImageView imageView=new ImageView(MainActivity.this);
//imageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
//return imageView;

//ʹ��SD��ͼƬ
SmartImageView smartImageView=new SmartImageView(MainActivity.this);
smartImageView.setImageURI(Uri.fromFile((File)imageInfo.image));
return smartImageView;

//ʹ��SmartImageView
//SmartImageView smartImageView=new SmartImageView(MainActivity.this);
//smartImageView.setImageResource(Integer.parseInt(imageInfo.image.toString()));
//return smartImageView;

//ʹ��BitmapUtils
BitmapUtils bitmapUtils=new BitmapUtils(MainActivity.this);
ImageView imageView=new ImageView(MainActivity.this);
bitmapUtils.display(imageView,imageInfo.image.toString());
return imageView;
}
});
 *
 *
 * </Pre>
 * @author ������
 *
 */
public class ImageCycleView extends FrameLayout {

	/**
	 * ������
	 */
	private Context mContext;
	/**
	 * ͼƬ�ֲ���ͼ
	 */
	private ImageCycleViewPager mViewPager;
	/**
	 * ���ݼ���
	 * Map<String,String> map=new HashMap<String, String>();
	 * map.put("","");
	 *
	 */
	private List<ImageInfo> data=new ArrayList<ImageInfo>();
	/**
	 * ����ͼƬ�ص�����
	 */
	private LoadImageCallBack mLoadImageCallBack;

	/**
	 * ͼƬ�ֲ�ָʾ������
	 */
	private LinearLayout mIndicationGroup;
	/**
	 * �ֲ�������
	 */
	private int mCount=0;
	/**
	 * δ��ý���ָʾ����Դ
	 */
	private Bitmap unFocusIndicationStyle;
	/**
	 * ��ý���ָʾ����Դ
	 */
	private Bitmap focusIndicationStyle;
	/**
	 * ָʾ��������������İٷֱ�,Ĭ�ϼ��Ϊָʾ���߶ȵ�1/2
	 */
	private float indication_self_margin_percent=0.5f;
	/**
	 * �����¼�������
	 */
	private OnPageClickListener mOnPageClickListener;
	/**
	 * ͼƬ�ı���ʾ
	 */
	private TextView mText;


	public ImageCycleView(Context context) {
		super(context);
		init(context);
	}

	public ImageCycleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ��ʼ��������Ϣ
	 * @param context
	 */
	private void init(Context context){
		mContext=context;
		unFocusIndicationStyle=drawCircle(50,Color.GRAY);
		focusIndicationStyle=drawCircle(50,Color.WHITE);
		initView();
	}

	/**
	 * ��ʼ��view�ؼ�
	 * @author ������
	 */
	private void initView() {
		View.inflate(mContext, R.layout.view_image_cycle, this);
		FrameLayout fl_image_cycle = (FrameLayout) findViewById(R.id.fl_image_cycle);
		mViewPager=new ImageCycleViewPager(mContext);
		mViewPager.setLayoutParams(new  ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
		fl_image_cycle.addView(mViewPager);
		mViewPager.setOnPageChangeListener(new ImageCyclePageChangeListener());
		mIndicationGroup = (LinearLayout) findViewById(R.id.ll_indication_group);
		mText=(TextView)findViewById(R.id.tv_text);
	}

	public enum IndicationStyle{
		COLOR,IMAGE
	}

	/**
	 * �����ֲ�ָʾ����ʽ��������Ĭ�ϵ���ʽ����������Լ�����
	 * @param indicationStyle ��Դ����,color,image,shape
	 * @param unFocus δ��ý���ָʾ����Դid  ͼƬ��shape��colorֵ
	 * @param focus ��ý���ָʾ����Դid ͼƬ��shape��colorֵ
	 * @param indication_self_percent ����߶ȵİٷֱ� >=0f
	 */
	public void setIndicationStyle(IndicationStyle indicationStyle,int unFocus,int focus,float indication_self_percent){
		if(indicationStyle== IndicationStyle.COLOR){
			unFocusIndicationStyle=drawCircle(50,unFocus);
			focusIndicationStyle=drawCircle(50,focus);
		}else if(indicationStyle== IndicationStyle.IMAGE){
			unFocusIndicationStyle= BitmapFactory.decodeResource(mContext.getResources(),unFocus);
			focusIndicationStyle=BitmapFactory.decodeResource(mContext.getResources(), focus);
		}
		indication_self_margin_percent=indication_self_percent;
		initIndication();
	}

	/**
	 * ͼƬ�ֲ����Զ�����״̬  true �Զ�������false ͼƬ�����Զ�����ֻ���ֶ����һ���
	 */
	private boolean isAutoCycle=true;
	/**
	 *�Զ��ֲ�ʱ����Ĭ��5��
	 */
	private long mCycleDelayed=5000;

	/**
	 * �����Ƿ��Զ������ֲ�
	 * @param delayed  �Զ��ֲ�ʱ����
	 */
	public void setCycleDelayed(long delayed){
		mCycleDelayed=delayed;
	}

	/**
	 * �����Ƿ��Զ������ֲ�
	 * @param state
	 */
	public void setAutoCycle(Boolean state){
		isAutoCycle=state;
	}

	/**
	 * ������ʾ������  ����ͼƬ��Դ������
	 * @param list       ����
	 * @param callBack   ��μ���ͼƬ����ʾ�Ļص����� not null
	 */
	public void loadData(List<ImageInfo> list,LoadImageCallBack callBack){
		data=list;
		mCount=list.size();
		initIndication();
		if(callBack==null){
			new IllegalArgumentException("LoadImageCallBack �ص���������Ϊ�գ�");
		}
		mLoadImageCallBack=callBack;
		mViewPager.setAdapter(new ImageCycleAdapter());
		//���ֵ�м� �ĵ�һ��
		mViewPager.setCurrentItem(Integer.MAX_VALUE/2-((Integer.MAX_VALUE/2)%mCount));
	}

	/**
	 * ���õ���¼������ص�����
	 * @param listener
	 */
	public void setOnPageClickListener(OnPageClickListener listener){
		mOnPageClickListener=listener;
	}

	/**
	 * �ֲ��ؼ��ļ����¼�
	 */
	public interface OnPageClickListener {
		/**
		 * ����ͼƬ�¼�
		 * @param imageView �������View����
		 * @param imageInfo ������Ϣ
		 */
		void onClick(View imageView, ImageInfo imageInfo);
	}


	/**
	 * ��ʼ��ָ����
	 */
	private void initIndication(){
		mIndicationGroup.removeAllViews();
		for(int i=0;i<mCount;i++){
			ImageView imageView = new ImageView(mContext);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicationGroup.getLayoutParams().height, LinearLayout.LayoutParams.MATCH_PARENT);
			params.leftMargin = (int)(mIndicationGroup.getLayoutParams().height*indication_self_margin_percent);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(params);
			if(i==0) {
				imageView.setImageBitmap(focusIndicationStyle);
			}else{
				imageView.setImageBitmap(unFocusIndicationStyle);
			}
			mIndicationGroup.addView(imageView);
		}
	}

	private Bitmap drawCircle(int radius,int color){
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);// ������ɫ
		Bitmap bitmap=Bitmap.createBitmap(radius,radius, Bitmap.Config.ARGB_8888);
		Canvas canvas=new Canvas(bitmap);
		canvas.drawCircle(radius / 2, radius / 2, radius / 2, paint);
		return bitmap;
	}


	public static class ImageInfo{
		public ImageInfo(Object image, String text, Object value) {
			this.image = image;
			this.text = text;
			this.value = value;
		}
		public Object image;
		public String text="";
		public Object value;
	}


	/**
	 * ����ͼƬ����ʾ�ص��ӿ�
	 */
	public interface LoadImageCallBack{
		/**
		 * �Լ�������ü���ͼƬ
		 * @param imageInfo  ������Ϣ
		 */
		ImageView loadAndDisplay(ImageInfo imageInfo);
	}

	/**
	 * �ֲ�ͼƬ����
	 * @author ������
	 */
	private final class ImageCyclePageChangeListener implements ViewPager.OnPageChangeListener {

		//�ϴ�ָʾ��ָʾ��λ��,��ʼΪĬ��λ��0
		private int preIndex=0;

		@Override
		public void onPageSelected(int index) {
			index=index%mCount;
			//�����ı���Ϣ
			String text=data.get(index).text;
			mText.setText(TextUtils.isEmpty(text)?"":text);
			//�ָ�Ĭ��û�л�ý���ָʾ����ʽ
			((ImageView)(mIndicationGroup.getChildAt(preIndex))).setImageBitmap(unFocusIndicationStyle);
			// ���õ�ǰ��ʾͼƬ��ָʾ����ʽ
			((ImageView)(mIndicationGroup.getChildAt(index))).setImageBitmap(focusIndicationStyle);
			preIndex=index;
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onPageScrolled(int i, float v, int i1) {

		}
	}

	/**
	 * ͼƬ�ֲ�������
	 */
	private class ImageCycleAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			final ImageInfo imageInfo = data.get(position % mCount);

			ImageView imageView=mLoadImageCallBack.loadAndDisplay(imageInfo);
			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			// ����ͼƬ�������
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(mOnPageClickListener!=null) {
						mOnPageClickListener.onClick(v,imageInfo);
					}
				}
			});

			container.addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
	}


	/**
	 * ��ʼͼƬ�ֲ�
	 */
	private void startImageCycle() {
		handler.sendEmptyMessageDelayed(0, mCycleDelayed);
	}

	/**
	 * ��ͣͼƬ�ֲ�
	 */
	private void stopImageCycle() {
		handler.removeCallbacksAndMessages(null);
	}

	/**
	 * ʵ���Զ��ֲ�
	 */
	private Handler handler=new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (mViewPager != null) {
				mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
				handler.sendEmptyMessageDelayed(0,mCycleDelayed);
			}
			return false;
		}
	});

	/**
	 * ����ֹͣ��ʱ����̧��������ʱ��
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_UP){
			if(isAutoCycle) {
				// ��ʼͼƬ����
				startImageCycle();
			}
		}else{
			if(isAutoCycle) {
				// ֹͣͼƬ����
				stopImageCycle();
			}
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		// ֹͣͼƬ����
		stopImageCycle();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(isAutoCycle) {
			startImageCycle();
		}
	}


	/**
	 * �Զ���ViewPager��Ҫ�����¼�����
	 */
	public class ImageCycleViewPager  extends ViewPager {

		public ImageCycleViewPager(Context context) {
			super(context);
		}

		public ImageCycleViewPager(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		/**
		 * �¼�����
		 */
		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			return super.onInterceptTouchEvent(ev);
		}

		/**
		 * �¼��ַ�
		 */
		@Override
		public boolean dispatchTouchEvent(MotionEvent ev) {
			getParent().requestDisallowInterceptTouchEvent(true);
			return super.dispatchTouchEvent(ev);
		}
		/**
		 * �¼�����
		 */
		@Override
		public boolean onTouchEvent(MotionEvent ev) {
			return super.onTouchEvent(ev);
		}


	}

}
