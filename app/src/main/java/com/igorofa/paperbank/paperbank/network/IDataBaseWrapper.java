package com.igorofa.paperbank.paperbank.network;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by laz on 3/04/17.
 */

public interface IDataBaseWrapper{
    <T> void storeInDataBase(@NonNull List<T> items);
}
