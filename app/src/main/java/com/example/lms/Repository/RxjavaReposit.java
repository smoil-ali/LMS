package com.example.lms.Repository;

import android.util.Log;

import com.example.lms.Listener.CategoryListener;
import com.example.lms.Model.CategoryResponse;
import com.example.lms.Retorfit.AcademyApis;
import com.example.lms.Retorfit.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxjavaReposit {
    AcademyApis academyApis;
    private final String TAG = RxjavaReposit.class.getSimpleName();
    CategoryListener categoryListener;

    public RxjavaReposit() {
        academyApis = RetrofitService.createService(AcademyApis.class);
        Observable<CategoryResponse> responseObservable = academyApis.getCategoriesUsingRxJava();
        responseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG,"subscribed");
                    }

                    @Override
                    public void onNext(CategoryResponse categoryResponse) {
                        Log.i(TAG,categoryResponse.getData().size()+"");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG,e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG,"complete");
                    }
                });
    }

    public void setCategoryListener(CategoryListener categoryListener){
        this.categoryListener = categoryListener;
    }
}
