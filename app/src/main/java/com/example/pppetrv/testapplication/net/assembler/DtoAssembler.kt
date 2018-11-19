package com.example.pppetrv.testapplication.net.assembler

interface DtoAssembler<D, S> {
    fun assemble(source: S): D
}
