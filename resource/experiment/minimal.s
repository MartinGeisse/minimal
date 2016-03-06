; Hello World example for OSX asm code
; kuban.altan@gmail.com
; Tested on OSX 10.10.3 (might fail after 10.10.5, since Apple now limited min. mach-o file to 4K)
 
; To make it run, just type these in Terminal (you might need Xcode installed)
; nasm -f bin -o Hello255bytes Hello255bytes.s
; chmod 755 Hello255bytes
; ./Hello255bytes
; Hello world.
; So the code below is typing "Hello world." in just 255 bytes of executable
 
; Inspired by the document below:
; http://osxbook.com/blog/2009/03/15/crafting-a-tiny-mach-o-executable/
 
BITS 32
    org 0x1000
 
    db  0xce, 0xfa, 0xed, 0xfe          ; magic number for mach-o executable
    dd  7                               ; cputype (CPU_TYPE_X86)
    dd  3                               ; cpusubtype (CPU_SUBTYPE_I386_ALL)
    dd  2                               ; filetype (MH_EXECUTE)
    dd  2                               ; ncmds
    dd  _start - _cmds                  ; cmdsize
    dd  0                               ; flags
_cmds:
    dd  1                               ; cmd (LC_SEGMENT)
    dd  124                             ; cmdsize
 _hello:
    db  "Hello world.   ", 10           ; Overwrite segname
    dd  0x1000                          ; vmaddr
    dd  0x1000                          ; vmsize
    dd  0                               ; fileoff
    dd  filesize                        ; filesize
    dd  7                               ; maxprot
    dd  5                               ; initprot
    dd  1                               ; nsects
    dd  0                               ; flags
    db  "__text"                        ; sectname
    db  0, 0, 0, 0, 0, 0, 0, 0, 0, 0    ; sectname
    db  "__TEXT"                        ; segname
    db  0, 0, 0, 0, 0, 0, 0, 0, 0, 0    ; segname
    dd  _start                          ; addr
    dd  _end - _start                   ; size;
    dd  _start - 0x1000                 ; offset
    dd  2                               ; align
    dd  0                               ; reloff
    dd  0                               ; nreloc
    dd  0                               ; flags
    dd  0                               ; reserved1
    dd  0                               ; reserved2
 
    dd  5                               ; cmd (LC_UNIXTHREAD)
    dd  80                              ; cmdsize
    dd  1                               ; flavor (i386_THREAD_STATE)
    dd  16                              ; count (i386_THREAD_STATE_COUNT)
    dd  0, 0, 0, 0, 0, 0, 0, 0          ; state
    dd  0, 0, _start, 0, 0, 0, 0, 0     ; state
 
; Our code starts here, and it assumes eax=0 and ebx=0, when kernel jumps to _start:
; I tested this on my 10.10.3 machine and it works!
 
_start:
    inc     ebx             ; ebx = 1
    shl     ebx,4           ; ebx = 16
    push    ebx             ; We push 16 as parameter to stack, which represents string length
 
    push    dword _hello    ; We push the address of our Hello world string
 
    inc     eax             ; eax = 1
    push    dword eax       ; push 1 as the parameter file descriptor (stdout)
 
    shl     eax, 2          ; eax = 4 so we will syscall (write)
    sub     esp, eax
    int     0x80            ; syscall
 
    xor eax, eax            ; eax = 0 (nosys) This is really a hack! Normally I should
                            ; call (exit) which needs eax = 1, but it would consume 2 more bytes
                            ; So I just call nosys, and exit my Hello world.
                            ; I know it is not legal. But I had to fit under 256 bytes!
    int 0x80                ; syscall
 
    resb 4096

_end:
filesize equ    $ - $$
