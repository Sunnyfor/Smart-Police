package com.zhkj.smartpolice.utils.fingerprint;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

interface IBiometricPromptImpl {

    void authenticate(@NonNull CancellationSignal cancel,
                      @NonNull BiometricPromptManager.OnBiometricIdentifyCallback callback);
}