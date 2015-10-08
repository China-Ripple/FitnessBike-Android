package com.signalripple.fitnessbike.bluetooth;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.signalripple.fitnessbike.api.API;
import com.signalripple.fitnessbike.utils.ToastUtil;

public class BlueTooth {
	
	private BluetoothAdapter bluetoothAdapter;
	private Context context;
	
	public BlueTooth(Context context)
	{
		this.context = context;
	}
	
	/**
	 * 打开蓝牙
	 */
	public void openBlueTooth()
	{
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		// 设备不支持蓝牙      
		if (bluetoothAdapter == null)    
		{
			ToastUtil.show(context, "设备不支持蓝牙功能", ToastUtil.ERROR);
		}
		// 打开蓝牙   
		if (!bluetoothAdapter.isEnabled())  
		{  
			Log.i("XU", "蓝牙已打开");
		    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  
		    // 设置蓝牙可见性，最多300秒   
		    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);  
		    context.startActivity(intent);  
		} 
	}
	
	/**
	 * 搜索设备
	 */
	public void searchDevices()
	{
		// 设置广播信息过滤   
		IntentFilter intentFilter = new IntentFilter();  
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);  
		intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);  
		intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);  
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);  
		// 注册广播接收器，接收并处理搜索结果   
		context.registerReceiver(receiver, intentFilter);  
		// 寻找蓝牙设备，android会将查找到的设备以广播形式发出去   
		bluetoothAdapter.startDiscovery();  
	}
	
	/**
	 * 连接设备
	 */
	public void connDevices()
	{
		
	}
	
	/**
	 * 停止搜索设备
	 */
	public void stopSearchDevices()
	{
		
	}
	
	/**
	 * 获取已经配对的设备
	 * @return
	 */
	public Set<BluetoothDevice> getAleadyPairDevices()
	{
		Log.i("XU", "读取已匹配设备");
		Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();    
		for(int i=0; i<devices.size(); i++)    
		{    
		   BluetoothDevice device = (BluetoothDevice) devices.iterator().next();    
		   System.out.println(device.getName());    
		   Log.i("XU", "已匹配设备："+device.getName());
		}   
		return devices;
	}
	
	int connectState;
	String name = "RT1050";
	private BroadcastReceiver receiver = new BroadcastReceiver() {    
	    @Override    
	    public void onReceive(Context context, Intent intent) { 
	    	
	        String action = intent.getAction();    
	        if (BluetoothDevice.ACTION_FOUND.equals(action)) {    
	            // 获取查找到的蓝牙设备      
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);    
	            
	            Log.i("XU", "找到蓝牙设备:"+device.getName());
	            
	            // 如果查找到的设备符合要连接的设备，处理      
	            if (device.getName().equalsIgnoreCase(name)) {
	            	
	            	
	                // 搜索蓝牙设备的过程占用资源比较多，一旦找到需要连接的设备后需要及时关闭搜索      
	                bluetoothAdapter.cancelDiscovery();    
	                // 获取蓝牙设备的连接状态      
	                connectState = device.getBondState();    
	                switch (connectState) {    
	                    // 未配对      
	                    case BluetoothDevice.BOND_NONE:    
	                        // 配对      
	                        try {
	                            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");    
	                            Object object = createBondMethod.invoke(device);    
	                            connect(device);  
	                            Log.i("XU", "匹配完成结果："+object.toString());
	                            getAleadyPairDevices();
	                        } catch (Exception e) {   
	                        	Log.i("XU", "配对异常="+e.toString());
	                            e.printStackTrace();    
	                        }    
	                        break;    
	                    // 已配对      
	                    case BluetoothDevice.BOND_BONDED:    
	                            // 连接      
	                           connect(device);    
	                        break;    
	                }    
	            }    
	       } else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {    
	            // 状态改变的广播      
	            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);    
	            if (device.getName().equalsIgnoreCase(name)) {     
	                connectState = device.getBondState();    
	                switch (connectState) {    
	                    case BluetoothDevice.BOND_NONE:    
	                        break;    
	                    case BluetoothDevice.BOND_BONDING:    
	                        break;    
	                    case BluetoothDevice.BOND_BONDED:    
	                        // 连接      
	                        connect(device);    
	                        break;    
	                }    
	            }    
	        }    
	    }    
	}   ; 

	private void connect(BluetoothDevice device){    
		Log.i("XU", "即将开始连接");
	    // 固定的UUID 		 11223344-5566-7788-99AA-BBCCDDEEFF00
	    final String SPP_UUID = API.BLUE_TOOTH_API_UUID;    
	    UUID uuid = UUID.fromString(SPP_UUID);  

	    BluetoothSocket socket;
		try {
			socket = device.createRfcommSocketToServiceRecord(uuid);
			socket.connect();    
			Log.i("XU", "连接成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//蓝牙连接异常=java.io.IOException: Service discovery failed
			Log.i("XU", "蓝牙连接异常="+e.toString());
			Toast.makeText(context, "蓝牙连接异常:"+e.toString(), Toast.LENGTH_LONG).show();
		}
	}   
	
//	private Boolean connect_result = false;
//	private BluetoothSocket bluetoothSocket = null;

	
}
