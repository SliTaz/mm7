package com.zbensoft.mmsmp.corebiz.route;

import java.io.Serializable;

public abstract interface IMessageRouter
{
  public abstract void doRouter(Serializable paramSerializable);
}





