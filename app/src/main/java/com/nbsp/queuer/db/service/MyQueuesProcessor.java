package com.nbsp.queuer.db.service;

import com.nbsp.queuer.api.Api;
import com.nbsp.queuer.db.DB;
import com.nbsp.queuer.db.entity.QueueWithMembers;

import java.util.List;

import rx.Subscriber;

/**
 * Created by egor on 20.12.15.
 */
public class MyQueuesProcessor extends BaseProcessor {

    void getQueues(final ProcessorCallback callback) {


        Api.getInstance().getQueues()
                .subscribe(new Subscriber<List<QueueWithMembers>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.send(RESULT_CODE_ERROR);
                    }

                    @Override
                    public void onNext(List<QueueWithMembers> queues) {
                        updateDb(queues);
                        callback.send(RESULT_CODE_OK);
                    }
                });

    }

    private void updateDb(List<QueueWithMembers> queues) {
        DB.getInstance().put().objects(queues).prepare().executeAsBlocking();
    }


}
