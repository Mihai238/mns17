# Android Cell Infos

## Setup
  1. download android studio
  1. import the project

### Setup emulator - Linux
  1. make sure your processor supports virtualization: `egrep --color=auto 'vmx|svm|0xc0f' /proc/cpuinfo`
  1. make sure virtualization is enabled: `zgrep CONFIG_KVM /proc/config.gz`, otherwise enable it in BIOS
  1. rename `Android/Sdk/tools/lib64/libstdc++` to some backup folder