package com.serenegiant.janus;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TransactionManager {
	private static class RandomString {
		final String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random rnd = new Random();

		public String get(int length) {
			final StringBuilder sb = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				sb.append(str.charAt(rnd.nextInt(str.length())));
			}
			return sb.toString();
		}
	}

	private static final RandomString mRandomString = new RandomString();
	private static final Map<String, TransactionCallback>
		sTransactions = new HashMap<String, TransactionCallback>();
	
	/**
	 * get transaction and assign it to specific callback
	 * @param length
	 * @param callback
	 * @return
	 */
	public static String get(int length, @Nullable final TransactionCallback callback) {
		final String transaction = mRandomString.get(length);
		if (callback != null) {
			synchronized (sTransactions) {
				sTransactions.put(transaction, callback);
			}
		}
		return transaction;
	}
	
	public interface TransactionCallback {
		public boolean onSuccess(final JSONObject object);
	}
	
	/**
	 * call callback related to the specific transaction
	 * @param transaction
	 * @param json
	 * @return
	 */
	public static boolean handleTransaction(
		@NonNull final String transaction,
		@NonNull final JSONObject json) {
		
		TransactionCallback callback = null;
		synchronized (sTransactions) {
			if (sTransactions.containsKey(transaction)) {
				callback = sTransactions.remove(transaction);
			}
		}
		return callback != null && callback.onSuccess(json);
	}
	
	/**
	 * clear transaction - callback mapping
	 */
	public static void clearTransactions() {
		synchronized (sTransactions) {
			sTransactions.clear();
		}
	}
}
