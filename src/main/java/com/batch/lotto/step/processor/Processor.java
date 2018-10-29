package com.batch.lotto.step.processor;

import com.batch.lotto.to.Lottery;
import com.google.appengine.api.datastore.Entity;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;

public class Processor implements ItemProcessor<List<Lottery>, List<Entity>> {

    @Override
    public List<Entity> process(List<Lottery> lotteries) {
        return null;
    }
}
