package com.chenbit.demo.test.sso;

/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Work\\Src\\nqmdm-dfhk\\NSkyEmmSDK\\src\\main\\aidl\\com\\nationsky\\emmsdk\\service\\aidl\\IEmmSso.aidl
 */
public interface IEmmSso extends android.os.IInterface {
//    /**
//     * Local-side IPC implementation stub class.
//     */
//    public static abstract class Stub extends android.os.Binder implements com.nationsky.emmsdk.service.aidl.IEmmSso {
//        private static final java.lang.String DESCRIPTOR = "com.nationsky.emmsdk.service.aidl.IEmmSso";
//
//        /**
//         * Construct the stub at attach it to the interface.
//         */
//        public Stub() {
//            this.attachInterface(this, DESCRIPTOR);
//        }
//
//        /**
//         * Cast an IBinder object into an com.nationsky.emmsdk.service.aidl.IEmmSso interface,
//         * generating a proxy if needed.
//         */
//        public static com.nationsky.emmsdk.service.aidl.IEmmSso asInterface(android.os.IBinder obj) {
//            if ((obj == null)) {
//                return null;
//            }
//            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
//            if (((iin != null) && (iin instanceof com.nationsky.emmsdk.service.aidl.IEmmSso))) {
//                return ((com.nationsky.emmsdk.service.aidl.IEmmSso) iin);
//            }
//            return new com.nationsky.emmsdk.service.aidl.IEmmSso.Stub.Proxy(obj);
//        }
//
//        @Override
//        public android.os.IBinder asBinder() {
//            return this;
//        }
//
//        @Override
//        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
//            switch (code) {
//                case INTERFACE_TRANSACTION: {
//                    reply.writeString(DESCRIPTOR);
//                    return true;
//                }
//                case TRANSACTION_requestSso: {
//                    data.enforceInterface(DESCRIPTOR);
//                    java.lang.String _arg0;
//                    _arg0 = data.readString();
//                    com.nationsky.emmsdk.service.aidl.IEmmSsoCallback _arg1;
//                    _arg1 = com.nationsky.emmsdk.service.aidl.IEmmSsoCallback.Stub.asInterface(data.readStrongBinder());
//                    this.requestSso(_arg0, _arg1);
//                    reply.writeNoException();
//                    return true;
//                }
//                case TRANSACTION_getTokenInfo: {
//                    data.enforceInterface(DESCRIPTOR);
//                    java.lang.String _arg0;
//                    _arg0 = data.readString();
//                    com.nationsky.emmsdk.service.aidl.IEmmSsoCallback _arg1;
//                    _arg1 = com.nationsky.emmsdk.service.aidl.IEmmSsoCallback.Stub.asInterface(data.readStrongBinder());
//                    this.getTokenInfo(_arg0, _arg1);
//                    reply.writeNoException();
//                    return true;
//                }
//            }
//            return super.onTransact(code, data, reply, flags);
//        }
//
//        private static class Proxy implements com.nationsky.emmsdk.service.aidl.IEmmSso {
//            private android.os.IBinder mRemote;
//
//            Proxy(android.os.IBinder remote) {
//                mRemote = remote;
//            }
//
//            @Override
//            public android.os.IBinder asBinder() {
//                return mRemote;
//            }
//
//            public java.lang.String getInterfaceDescriptor() {
//                return DESCRIPTOR;
//            }
//
//            /**
//             * App通知UEM单点登录
//             *
//             * @param pkgName  app包名
//             * @param callback 处理结果回调
//             */
//            @Override
//            public void requestSso(java.lang.String pkgName, com.nationsky.emmsdk.service.aidl.IEmmSsoCallback callback) throws android.os.RemoteException {
//                android.os.Parcel _data = android.os.Parcel.obtain();
//                android.os.Parcel _reply = android.os.Parcel.obtain();
//                try {
//                    _data.writeInterfaceToken(DESCRIPTOR);
//                    _data.writeString(pkgName);
//                    _data.writeStrongBinder((((callback != null)) ? (callback.asBinder()) : (null)));
//                    mRemote.transact(Stub.TRANSACTION_requestSso, _data, _reply, 0);
//                    _reply.readException();
//                } finally {
//                    _reply.recycle();
//                    _data.recycle();
//                }
//            }
//
//            /**
//             * App通知UEM获取token
//             */
//            @Override
//            public void getTokenInfo(java.lang.String pkgName, com.nationsky.emmsdk.service.aidl.IEmmSsoCallback callback) throws android.os.RemoteException {
//                android.os.Parcel _data = android.os.Parcel.obtain();
//                android.os.Parcel _reply = android.os.Parcel.obtain();
//                try {
//                    _data.writeInterfaceToken(DESCRIPTOR);
//                    _data.writeString(pkgName);
//                    _data.writeStrongBinder((((callback != null)) ? (callback.asBinder()) : (null)));
//                    mRemote.transact(Stub.TRANSACTION_getTokenInfo, _data, _reply, 0);
//                    _reply.readException();
//                } finally {
//                    _reply.recycle();
//                    _data.recycle();
//                }
//            }
//        }
//
//        static final int TRANSACTION_requestSso = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
//        static final int TRANSACTION_getTokenInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
//    }
//
//    /**
//     * App通知UEM单点登录
//     *
//     * @param pkgName  app包名
//     * @param callback 处理结果回调
//     */
//    public void requestSso(java.lang.String pkgName, com.nationsky.emmsdk.service.aidl.IEmmSsoCallback callback) throws android.os.RemoteException;
//
//    /**
//     * App通知UEM获取token
//     */
//    public void getTokenInfo(java.lang.String pkgName, com.nationsky.emmsdk.service.aidl.IEmmSsoCallback callback) throws android.os.RemoteException;
}
