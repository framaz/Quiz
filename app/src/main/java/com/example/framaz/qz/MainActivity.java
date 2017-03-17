package com.example.framaz.qz;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	private AbsoluteLayout Abs;
	private Button Question;
	private Button[] Answers=new Button[4];
	private double width;
	private double height;
	private static final int QUESTIONS=8;
	private static final int VARIANTS=4;
	private static final char DELIMITER='/';
	private String[][] AnsMatrix=new String[VARIANTS][QUESTIONS];
	private int[] RightAnswers=new int[QUESTIONS];
	private String[] Ques=new String[QUESTIONS];
	private TypedArray Base;
	private int time=0;
	private int total_time=3;
	private int right=0;
	private int wrong=0;
	private int current_right=0;
	private AbsoluteLayout.LayoutParams[] params=new AbsoluteLayout.LayoutParams[VARIANTS+1];
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try{
           super.onCreate(savedInstanceState);
           Abs=new AbsoluteLayout(this);
           setContentView(Abs);
           Display display = getWindowManager().getDefaultDisplay(); 
           width=display.getWidth();
           height=display.getHeight();
           Question= new Button(this);
           params[0]=new AbsoluteLayout.LayoutParams(
                 (int)width,(int)height/4,0,0
           );
           Question.setLayoutParams(params[0]);
           Abs.addView(Question);
           Question.setOnClickListener(this);
           //
           for (int i=1;i<VARIANTS+1;i++){
              int k=i%2;
              int m;
              if (i<3) m=1;
              else{
            	  m=2;
              }
              Answers[i-1]= new Button(this);
              params[i]=new AbsoluteLayout.LayoutParams(
                    (int)width/2,(int)height/4,(int)(k*width/2),(int)(m*height/4)
              );
              Answers[i-1].setLayoutParams(params[i]);
              Abs.addView(Answers[i-1]);
              Answers[i-1].setOnClickListener(this);
           }
           //getSubstringBetweenDelimiters(2,3,"/Question1/Variant11/Variant12/Variant13/Variant14/Answer1/");
           //Toast.makeText(this, getSubstringBetweenDelimiters(5,6,"/Question1/Variant11/Variant12/Variant13/Variant14/Answer1/"), Toast.LENGTH_LONG).show();
           LoadQuestions();
           LoadQuestion();
    	}
    	catch(Exception e){
    		Toast.makeText(this, e+"", Toast.LENGTH_LONG).show();
    	}
    }
    
   private void LoadQuestion() {
         int qs=(int)(System.currentTimeMillis()%QUESTIONS);
         Question.setText(Ques[qs]);
         for (int i=0;i<VARIANTS;i++){
            Answers[i].setText(AnsMatrix[i][qs]);
         }
         current_right=RightAnswers[qs]-1;
   }
    
   private void LoadQuestions(){
    	Resources res=getResources();
        Base=res.obtainTypedArray(R.array.Questions);
        for (int i=0;i<QUESTIONS;i++){
        	Ques[i]=getSubstringBetweenDelimiters(0,1,Base.getString(i));
        	for (int j=0;j<VARIANTS;j++){
        		AnsMatrix[j][i]=getSubstringBetweenDelimiters(j+1,j+2,Base.getString(i));
        	}
        	RightAnswers[i]=Integer.parseInt(getSubstringBetweenDelimiters(VARIANTS+1,VARIANTS+2,Base.getString(i)));
        }
    }
   
    private String getSubstringBetweenDelimiters(int k, int m, String str){
    	int index1=0;
    	int index2=0;
    	int len=str.length();
    	int dels=0;
    	for (int i=0;i<len;i++){
    		if (str.charAt(i)==DELIMITER){
    			dels++;
    		}
    		if (dels==k){
    			index1=i;
    		}
    		if (dels==m){
    			index2=i;
    		}
    	}
        //Toast.makeText(this, index1+" "+index2, Toast.LENGTH_LONG).show();
    	return str.substring(index1+2, index2+1);
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0==Question){
			wrong++;
		}
		else{
			wrong++;
			for (int i=0;i<VARIANTS;i++){
				if (arg0==Answers[i]){
					if (current_right==i){
						wrong--;
						right++;
					}
				}
			}
		}
		time++;
		LoadQuestion();
		if (time==total_time){
			Stats();
			time=0;
			right=0;
			wrong=0;
		}
	}
	
	private void Stats() {
		// TODO Auto-generated method stub
		double rating=Math.round(((double)right/((double)right+(double)wrong))*100);
		String stat="";
		stat+=getString(R.string.note1);
		stat+=" "+right+" ";
		stat+=getString(R.string.note2);
		stat+=" "+total_time+". ";
		stat+=getString(R.string.note3);
		stat+=" "+(rating+"").substring(0,(rating+"").length()-2);
		Toast.makeText(this, stat, Toast.LENGTH_LONG).show();
	}
}