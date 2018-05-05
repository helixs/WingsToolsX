package com.helixs.tools;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.helixs.compat.Compat;

import java.util.Set;

/**
 * Created by helixs on 16/5/27.
 * sharedPrefs的封装,
 * 其中使用SharedPreferences传入构造,不在意多重实例,
 * 其单例由SharedPreferences保证,本包装类不会对此有任何处理
 * 当然也可以使用单例
 * 存取由其内部类成员(Pref子类)来实现
 */
public abstract class Prefs {
	protected final SharedPreferences sp;

	protected Prefs(SharedPreferences sp) {
		this.sp = sp;
	}

	public void clear(Pref... prefs) {
		SharedPreferences.Editor editor = sp.edit();
		for (Pref f : prefs) {
			editor.remove(f.name);
		}
		editor.apply();
	}

	public void clearAll() {
		sp.edit().clear().apply();
	}

	public abstract class Pref {
		protected final String name;

		protected Pref(@NonNull String name) {
			this.name = name;
		}

		public void clear() {
			sp.edit().remove(name).apply();
		}
	}

	public abstract class ObjPref<T> extends Pref {
		protected final T defaultValue;

		public ObjPref(@NonNull String name, T defaultValue) {
			super(name);
			this.defaultValue = defaultValue;
		}

		public ObjPref(String name) {
			this(name, null);
		}

		@Nullable
		public abstract T get();

		public abstract void set(T value);
	}

	public class BoolPref extends Pref {

		private final boolean defaultValue;

		public BoolPref(@NonNull String name, boolean defaultValue) {
			super(name);
			this.defaultValue = defaultValue;
		}

		public boolean get() {
			return sp.getBoolean(this.name, defaultValue);
		}

		public void set(boolean value) {
			sp.edit().putBoolean(name, value).apply();
		}
	}

	public class IntPref extends Pref {
		private final int defaultValue;

		public IntPref(String name, int defaultValue) {
			super(name);
			this.defaultValue = defaultValue;
		}

		public int get() {
			return sp.getInt(this.name, defaultValue);
		}

		public void set(int value) {
			sp.edit().putInt(name, value).apply();
		}
	}

	public class FloatPref extends Pref {
		private final float defaultValue;

		public FloatPref(String name, int defaultValue) {
			super(name);
			this.defaultValue = defaultValue;
		}

		public float get() {
			return sp.getFloat(this.name, defaultValue);
		}

		public void set(float value) {
			sp.edit().putFloat(name, value).apply();
		}
	}

	public class LongPref extends Pref {
		private final long defaultValue;

		public LongPref(String name, long defaultValue) {
			super(name);
			this.defaultValue = defaultValue;
		}

		public long get() {
			return sp.getLong(this.name, defaultValue);
		}

		public void set(long value) {
			sp.edit().putLong(name, value).apply();
		}
	}

	public class StringPref extends ObjPref<String> {
		public StringPref(String name) {
			super(name, null);
		}

		public StringPref(String name, String defaultValue) {
			super(name, defaultValue);
		}

		@Override
		public String get() {
			return sp.getString(name, defaultValue);
		}

		@Override
		public void set(String value) {
			sp.edit().putString(name, value).apply();
		}
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public class StringSetPref extends ObjPref<Set<String>> {
		public StringSetPref(String name, Set<String> defaultValue) {
			super(name, defaultValue);
		}

		public StringSetPref(String name) {
			super(name);
		}

		@Override
		@Nullable
		public Set<String> get() {
			return sp.getStringSet(name, defaultValue);
		}

		@Override
		public void set(Set<String> set) {
			sp.edit().putStringSet(name, set).apply();
		}

		public void add(String value) {
			Set<String> stringSet = get();
			if (stringSet == null) {
				stringSet = Compat.createSet();
			}
			stringSet.add(value);
			set(stringSet);
		}
	}

	public class CachedPref<T> {

		private final ObjPref<T> pref;
		private T value;

		public CachedPref(ObjPref<T> pref) {
			this.pref = pref;
			this.value = pref.get();
		}

		public T get() {
			if (value == null) {
				value = pref.get();
			}
			return value;
		}


		public void update(T value) {
			if (value != null && !value.equals(this.value)) {
				this.value = value;
				pref.set(value);
			}
		}

		public void clear() {
			value = null;
			pref.clear();
		}
	}
}
