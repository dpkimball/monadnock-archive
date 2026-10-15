package com.oxbow.monadnock.service;


import com.oxbow.monadnock.service.domain.TwinPeak;

public interface ApiDataCallback {
    void onTwinPeakResponse(TwinPeak twinpeak);
    void onAttachmentResponse();
}
