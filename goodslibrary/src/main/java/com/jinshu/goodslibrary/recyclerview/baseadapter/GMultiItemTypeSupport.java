package com.jinshu.goodslibrary.recyclerview.baseadapter;

public interface GMultiItemTypeSupport<T>
{
	int getLayoutId(int itemType);

	int getItemViewType(int position, T t);
}