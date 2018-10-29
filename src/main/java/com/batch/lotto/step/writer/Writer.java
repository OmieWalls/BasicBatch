package com.batch.lotto.step.writer;

import com.google.appengine.api.datastore.Entity;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class Writer implements ItemWriter<List<Entity>> {
    @Override
    public void write(List<? extends List<Entity>> list) {
    }
}
