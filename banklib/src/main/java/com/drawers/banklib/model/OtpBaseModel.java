package com.drawers.banklib.model;

import java.util.EnumMap;

public interface OtpBaseModel extends BaseModel {
  String getOtpInputSelector();
  EnumMap<ButtonModel.Type, ButtonModel> getButtons();
}
