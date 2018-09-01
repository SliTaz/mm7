package com.zbensoft.mmsmp.vac.ra.aaa;

import java.nio.ByteBuffer;

public abstract interface Serialize
{
  public abstract ByteBuffer serialize()
    throws Exception;

  public abstract ByteBuffer unserialize(ByteBuffer paramByteBuffer)
    throws Exception;
}
