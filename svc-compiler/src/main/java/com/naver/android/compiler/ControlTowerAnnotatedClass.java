package com.naver.android.compiler;

import com.google.common.base.VerifyException;
import com.squareup.javapoet.ClassName;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@SuppressWarnings("WeakerAccess")
public class ControlTowerAnnotatedClass {

  public final TypeElement annotatedElement;
  public final String packageName;
  public final String clazzName;
  public final ClassName baseView;
  public final String baseViewName;

  public ControlTowerAnnotatedClass(TypeElement annotatedElement, Elements elementUtils)
      throws VerifyException {
    PackageElement packageElement = elementUtils.getPackageOf(annotatedElement);
    this.packageName =
        packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
    this.annotatedElement = annotatedElement;
    this.clazzName = annotatedElement.getSimpleName().toString();

    String[] superClazzSp0 = annotatedElement.getSuperclass().toString().split("<");
    String[] superClazzSp1 = superClazzSp0[1].split(">");
    String viewPackageSp = superClazzSp1[0];
    int index = viewPackageSp.lastIndexOf(".");
    if (index == -1) index = viewPackageSp.lastIndexOf("\\.");

    this.baseViewName = viewPackageSp.substring(index + 1);
    this.baseView = ClassName.get(viewPackageSp.substring(0, index), baseViewName);
  }
}
