package com.naver.android.compiler;

import com.google.common.base.VerifyException;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@SuppressWarnings("WeakerAccess")
public class ControlTowerAnnotatedClass {

  public final TypeElement annotatedElement;
  public final String packageName;
  public final String clazzName;

  public ControlTowerAnnotatedClass(TypeElement annotatedElement, Elements elementUtils)
      throws VerifyException {
    PackageElement packageElement = elementUtils.getPackageOf(annotatedElement);
    this.packageName =
        packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
    this.annotatedElement = annotatedElement;
    this.clazzName = annotatedElement.getSimpleName().toString();
  }
}
