package com.amlogic.netfilebrowser.smbmnt;

import java.util.ArrayList;
import android.util.Log;

public class SmbClientMnt {
	public static final String TAG = "SmbClientMnt";
	private ArrayList<String> sharepaths = new ArrayList<String>();

	//-1 input username and psw
	public int SmbMountWrapper(String MntArg){
		Log.d(TAG, "SmbMount, mntarg: " + MntArg);
		int res = SmbMount(MntArg);
		Log.d(TAG, "SmbMount, res: " + res);
		return res;
	}
	
	private native int SmbMount(String MntArg);

	public int SmbUnMountWrapper(String mountPoint){
		Log.d(TAG, "SmbUnMount, mountPoint: " + mountPoint);
		int res = SmbUnMount(mountPoint);
		Log.d(TAG, "SmbUnMount, res: " + res);
		return res;
	}
	
	private native int SmbUnMount(String MountPoint);

	public int SmbRefreshWrapper(){
		Log.d(TAG, "SmbRefresh");
		int res = SmbRefresh();
		Log.d(TAG, "SmbRefresh, res: " + res);
		return res;
	}
	
	private native int SmbRefresh();

	
	public String SmbGetShareListWrapper(int fd){
		Log.d(TAG, "SmbGetShareList, fd: " + fd);
		String res = SmbGetShareList(fd);
		Log.d(TAG, "SmbGetShareList, res: " + res);
		return res;
	}
	private native String SmbGetShareList(int fd); 

	public int SmbGetStatusWrapper(int fd){
		Log.d(TAG, "SmbGetStatus, fd: " + fd);
		int res = SmbGetStatus(fd);
		Log.d(TAG, "SmbGetStatus, res: " + res);
		return res;
	}
	private native int SmbGetStatus(int fd); 

	public int SmbGetNumWrapper(){
		Log.d(TAG, "SmbGetNum");
		int res = SmbGetNum();
		Log.d(TAG, "SmbGetNum, res: " + res);
		return res;
	}
	private native int SmbGetNum(); 

	public void FreshList() {
		sharepaths.clear();
		SmbRefreshWrapper();
	}
	
	public int GetStatus(int type) {
		return 0;
	}
	
	public ArrayList<String> GetListNum() {
		String item = "";
		int total = SmbGetNumWrapper(); 
		Log.d(TAG,"Get ListNum:"+Integer.toString(total));
		if(total > 0) {
			sharepaths.clear();
			for(int i = 0; i < total;i++) {
				item =  SmbGetShareListWrapper(i);
				Log.d(TAG,"Got Item"+item);
				sharepaths.add(item);
			}
		}
		return sharepaths;
	}
	
	public String GetShareDir(int num) {
		if(num > sharepaths.size()) {
			return null;
		}
		else {
			return  sharepaths.get(num);
		}	 		 
	}
	
	static {
        System.loadLibrary("smbmnt");
    }
}