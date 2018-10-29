package com.batch.lotto.step.reader;

import com.batch.lotto.to.Lottery;
import org.springframework.batch.item.ItemReader;

import java.util.List;

public class Reader implements ItemReader<List<Lottery>> {
    @Override
    public List<Lottery> read() {
        return null;
    }
}
